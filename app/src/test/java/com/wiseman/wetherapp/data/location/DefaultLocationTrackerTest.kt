package com.wiseman.wetherapp.data.location

import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import arrow.core.Either
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.wiseman.wetherapp.TestUtil
import com.wiseman.wetherapp.util.Failure
import io.mockk.checkUnnecessaryStub
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.Exception

class DefaultLocationTrackerTest {

    private lateinit var defaultLocationTracker: DefaultLocationTracker
    private lateinit var mockFusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mockApplication: Application
    private lateinit var mockLocationManager: LocationManager
    private val locationData = TestUtil.getLocationData()
    private lateinit var mockTask: Task<Location>
    private lateinit var mockLocation: Location

    @BeforeEach
    fun setUp() {
        mockkStatic(ContextCompat::checkSelfPermission)
        mockFusedLocationProviderClient = mockk(relaxed = true)
        mockApplication = mockk(relaxed = true)
        mockLocationManager = mockk(relaxed = true)
        mockTask = mockk(relaxed = true)
        mockLocation = mockk(relaxed = true)

        defaultLocationTracker =
            DefaultLocationTracker(mockFusedLocationProviderClient, mockApplication)
        every { mockApplication.getSystemService(any()) } returns mockLocationManager

    }

    @AfterEach
    fun tearDown() {
        confirmVerified(mockApplication, mockFusedLocationProviderClient)
        checkUnnecessaryStub(mockApplication, mockFusedLocationProviderClient)
    }

    @Test
    fun shouldGetCurrentLocationWhenLastLocationIsCompleteAndSuccessful() = runTest {
        // given
        every {
            ContextCompat.checkSelfPermission(
                any(),
                any()
            )
        } returns PackageManager.PERMISSION_GRANTED
        every { mockLocationManager.isProviderEnabled(any()) } returns true
        every { mockFusedLocationProviderClient.lastLocation.isComplete } returns true
        every { mockFusedLocationProviderClient.lastLocation.isSuccessful } returns true
        every { mockFusedLocationProviderClient.lastLocation.result } returns mockLocation
        every { mockLocation.latitude } returns locationData.latitude
        every { mockLocation.longitude } returns locationData.longitude

        // when
        val location = defaultLocationTracker.getCurrentLocation()

        // then
        assertEquals(Either.Right(locationData), location)
        verify(exactly = ONCE) {
            mockApplication.getSystemService(any())
            mockFusedLocationProviderClient.lastLocation
            mockLocation.latitude
            mockLocation.longitude
        }
    }

    @Test
    fun shouldReturnFailureWhenPermissionIsDenied() = runTest {
        // given
        every {
            ContextCompat.checkSelfPermission(
                any(),
                any()
            )
        } returns PackageManager.PERMISSION_DENIED
        every { mockLocationManager.isProviderEnabled(any()) } returns false

        // when
        val location = defaultLocationTracker.getCurrentLocation()

        // then
        assertEquals(Either.Left(Failure.LocationPermissionError()), location)
        verify(exactly = ONCE) {
            mockApplication.getSystemService(any())
        }
    }

    @Test
    fun shouldReturnFailureWhenLocationTaskIsCompleteButNotSuccessful() = runTest {
        // given
        every {
            ContextCompat.checkSelfPermission(
                any(),
                any()
            )
        } returns PackageManager.PERMISSION_GRANTED
        every { mockLocationManager.isProviderEnabled(any()) } returns true
        every { mockFusedLocationProviderClient.lastLocation.isComplete } returns true
        every { mockFusedLocationProviderClient.lastLocation.isSuccessful } returns false
        // when
        val location = defaultLocationTracker.getCurrentLocation()

        // then
        assertEquals(Either.Left(Failure.LocationError()), location)
        verify(exactly = ONCE) {
            mockApplication.getSystemService(any())
            mockFusedLocationProviderClient.lastLocation
        }
    }

    @Test
    fun shouldReturnCurrentLocationDataWhenLastLocationIsNotCompleteAndOnSuccessListenerIsCall() =
        runTest {
            // given

            every {
                ContextCompat.checkSelfPermission(
                    any(),
                    any()
                )
            } returns PackageManager.PERMISSION_GRANTED
            every { mockLocationManager.isProviderEnabled(any()) } returns true
            every { mockFusedLocationProviderClient.lastLocation.isComplete } returns false
            every { mockLocation.longitude } returns locationData.longitude
            every { mockLocation.latitude } returns locationData.latitude
            every { mockTask.result } returns mockLocation
            every { mockFusedLocationProviderClient.lastLocation.addOnSuccessListener(any<OnSuccessListener<Location>>()) } answers {
                firstArg<OnSuccessListener<Location>>().onSuccess(mockLocation)
                mockTask
            }
            every { mockFusedLocationProviderClient.lastLocation.addOnFailureListener(any<OnFailureListener>()) } answers {
                mockTask
            }
            every { mockFusedLocationProviderClient.lastLocation.addOnCompleteListener(any<OnCompleteListener<Location>>()) } answers {
                mockTask
            }

            // when
            val location = defaultLocationTracker.getCurrentLocation()

            // then
            assertEquals(Either.Right(locationData), location)
            verify(exactly = ONCE) {
                mockApplication.getSystemService(any())
                mockFusedLocationProviderClient.lastLocation
            }
        }

    @Test
    fun shouldReturnFailureWhenLastLocationIsNotCompleteAndOnFailureIsCalled() = runTest {
        // given
        every {
            ContextCompat.checkSelfPermission(
                any(),
                any()
            )
        } returns PackageManager.PERMISSION_GRANTED
        every { mockLocationManager.isProviderEnabled(any()) } returns true
        every { mockFusedLocationProviderClient.lastLocation.isComplete } returns false
        every { mockLocation.longitude } returns locationData.longitude
        every { mockLocation.latitude } returns locationData.latitude
        every { mockTask.result } returns mockLocation
        every { mockFusedLocationProviderClient.lastLocation.addOnSuccessListener(any<OnSuccessListener<Location>>()) } answers {
            mockTask
        }
        every { mockFusedLocationProviderClient.lastLocation.addOnFailureListener(any<OnFailureListener>()) } answers {
            firstArg<OnFailureListener>().onFailure(Exception())
            mockTask
        }
        every { mockFusedLocationProviderClient.lastLocation.addOnCompleteListener(any<OnCompleteListener<Location>>()) } answers {
            mockTask
        }

        // when
        val location = defaultLocationTracker.getCurrentLocation()

        // then
        assertEquals(Either.Left(Failure.LocationError()), location)
        verify(exactly = ONCE) {
            mockApplication.getSystemService(any())
            mockFusedLocationProviderClient.lastLocation
            mockFusedLocationProviderClient.lastLocation.addOnFailureListener(any<OnFailureListener>())
        }
    }
    private companion object {
        const val ONCE = 1
    }
}
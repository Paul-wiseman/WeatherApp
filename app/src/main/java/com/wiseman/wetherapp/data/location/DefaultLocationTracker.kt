package com.wiseman.wetherapp.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import arrow.core.Either
import com.google.android.gms.location.FusedLocationProviderClient
import com.wiseman.wetherapp.R
import com.wiseman.wetherapp.util.Failure
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class DefaultLocationTracker @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application
) : LocationTracker {
    override suspend fun getCurrentLocation(): Either<Failure, LocationData> {
        val hasFineLocationAccessPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager =
            application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER
            )

        if (!hasCoarseLocationPermission || !hasFineLocationAccessPermission || !isGpsEnabled) {
            return Either.Left(Failure.LocationPermissionError(R.string.enable_location_permission_error))
        }

        return suspendCancellableCoroutine { cont: CancellableContinuation<Either<Failure, LocationData>> ->
            locationClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {

                        cont.resume(
                            Either.Right(
                                LocationData(
                                    latitude = result.latitude,
                                    longitude = result.longitude
                                )
                            )
                        )
                    } else {
                        cont.resume(Either.Left(Failure.LocationError(R.string.unable_to_get_current_location)))
                    }
                    return@suspendCancellableCoroutine
                }

                addOnSuccessListener { value: Location ->

                    cont.resume(Either.Right(LocationData(
                        latitude = value.latitude,
                        longitude = value.longitude
                    )))
                }
                addOnFailureListener {
                    cont.resume(Either.Left(Failure.LocationError(R.string.failed_to_get_current_location)))
                }

                addOnCompleteListener {
                    cont.cancel()
                }
            }
        }

    }
}
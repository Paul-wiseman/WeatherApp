package com.wiseman.wetherapp.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.wiseman.wetherapp.data.preference.LocationPreference
import com.wiseman.wetherapp.data.preference.WeatherAppDataStore
import com.wiseman.wetherapp.data.remote.WeatherApi
import com.wiseman.wetherapp.data.works.WorkStarter
import com.wiseman.wetherapp.util.coroutine.DispatchProvider
import com.wiseman.wetherapp.util.coroutine.DispatchProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()


    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): WeatherApi = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create()

    @Singleton
    @Provides
    fun providesFussedLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @Singleton
    @Provides
    fun providesRainWorkManager(@ApplicationContext context: Context) = WorkStarter(context)

    @Singleton
    @Provides
    fun providesLocationPreferences(@ApplicationContext context: Context): LocationPreference =
        WeatherAppDataStore(context)

    @Singleton
    @Provides
    fun providesDispatchProvider():DispatchProvider = DispatchProviderImpl()


    const val BASE_URL = "https://api.open-meteo.com/"
}
package com.example.venues.api

import com.example.venues.BuildConfig
import com.example.venues.data.VenueDetailsResponse
import com.example.venues.data.VenueSearchResponse
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*

interface FourSquareService {

    @GET("venues/search")
    suspend fun searchVenues(
        @Query("near") near: String,
        @Query("limit") limit: Int = LIMIT,
        @Query("client_id") clientId: String = BuildConfig.CLIENT_ID,
        @Query("client_secret") clientSecret: String = BuildConfig.CLIENT_SECRET,
        @Query("v") date: String = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
    ): VenueSearchResponse

    @GET("venues/{venue_id}")
    suspend fun getVenueDetails(
        @Path("venue_id") venueId: String,
        @Query("client_id") clientId: String = BuildConfig.CLIENT_ID,
        @Query("client_secret") clientSecret: String = BuildConfig.CLIENT_SECRET,
        @Query("v") date: String = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())

    ): VenueDetailsResponse

    companion object {
        private const val BASE_URL = "https://api.foursquare.com/v2/"
        const val LIMIT = 10

        fun create(): FourSquareService {
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FourSquareService::class.java)
        }
    }
}
package com.example.euphoria_colombo.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://raw.githubusercontent.com/HamraJekeen/Json/main/"

    // Build the Retrofit instance
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Set the base URL for the API
            .addConverterFactory(GsonConverterFactory.create()) // Add Gson for JSON parsing
            .build()
    }

    // Create the service instance
    val service: CouponService by lazy {
        retrofit.create(CouponService::class.java)
    }
}
package com.example.euphoria_colombo.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

object ProductServiceBuilder {
    private const val BASE_URL = "http://10.0.2.2:8000/" // Update to your server's URL
    private val okHttp = OkHttpClient.Builder() // Customize OkHttpClient if needed

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())
        .build()

    // Function to create a service of the specified type
    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType) // Create and return the service
    }
}
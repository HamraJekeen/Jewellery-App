package com.example.euphoria_colombo.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofits = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/")  // Your base URL here
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: ProductDetailApi = retrofits.create(ProductDetailApi::class.java)
}


//    val retrofit = Retrofit.Builder()
//        .baseUrl("https://raw.githubusercontent.com/HamraJekeen/ProductDetails/main/") // Base URL
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    val ProductDetailApi = retrofit.create(ProductDetailApi::class.java)

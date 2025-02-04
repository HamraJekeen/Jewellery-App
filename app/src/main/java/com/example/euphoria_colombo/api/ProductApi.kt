package com.example.euphoria_colombo.api

import com.example.euphoria_colombo.model.ProductResponse
import retrofit2.Call
import retrofit2.http.GET

interface ProductApi {
    @GET("api/products")
    fun getProducts(): Call<ProductResponse>
} 
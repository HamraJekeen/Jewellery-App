package com.example.euphoria_colombo.api

import com.example.euphoria_colombo.model.ProductResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ProductApi {
    @GET("api/products")
    fun getProducts(@Query("page") page: Int): Call<ProductResponse>
} 
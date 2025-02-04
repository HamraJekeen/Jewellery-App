package com.example.euphoria_colombo.api

import com.example.euphoria_colombo.model.ProductDetail
import retrofit2.http.GET

interface ProductDetailApi {
    @GET("HamraJekeen/ProductDetails/main/productdetails.json")
    //fun getProducts(): Call<List<Product>>
    suspend fun getProducts(): List<ProductDetail>
}
package com.example.euphoria_colombo.data.network

import com.example.euphoria_colombo.data.Coupon
import retrofit2.http.GET

interface CouponService {
    @GET("https://raw.githubusercontent.com/HamraJekeen/Json/main/data.json") // Replace with the relative path of your JSON file
    suspend fun getCoupons(): List<Coupon>
}
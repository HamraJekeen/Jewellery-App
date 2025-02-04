package com.example.euphoria_colombo.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.euphoria_colombo.data.Coupon
import com.example.euphoria_colombo.data.network.RetrofitClient
import kotlinx.coroutines.launch

class CouponViewModel : ViewModel() {

    // Private mutable state for holding the coupons list
    private val _coupons = mutableStateOf<List<Coupon>>(emptyList())

    // Public read-only state to expose coupons to the UI
    val coupons: State<List<Coupon>> get() = _coupons

    private val _showCoupon = mutableStateOf(false)
    val showCoupon: State<Boolean> = _showCoupon

    fun setShowCoupon(show: Boolean) {
        _showCoupon.value = show
    }

    // Fetch coupons from the network
    fun fetchCoupons() {
        viewModelScope.launch {
            try {
                // Make the network call to get the coupons
                val response = RetrofitClient.service.getCoupons()

                // Update the coupons state with the fetched data
                _coupons.value = response
            } catch (e: Exception) {
                // Handle errors (e.g., log, show fallback message, etc.)
                _coupons.value = emptyList() // Empty list in case of failure
            }
        }
    }
}

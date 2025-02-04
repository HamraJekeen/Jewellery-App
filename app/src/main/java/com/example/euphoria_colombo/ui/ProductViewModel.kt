package com.example.euphoria_colombo.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.euphoria_colombo.api.RetrofitInstance
import com.example.euphoria_colombo.model.ProductDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Callback
class ProductViewModel : ViewModel() {

    // Private mutable state for holding the list of products
    private val _products = mutableStateOf<List<ProductDetail>>(emptyList())

    // Public read-only state to expose products to the UI
    val products: State<List<ProductDetail>> get() = _products

    // Fetch all products from the network
    fun fetchProducts() {
        viewModelScope.launch {
            try {
                // Make the network call to get the products
                val response = RetrofitInstance.service.getProducts()  // Using suspend function

                // Update the products state with the fetched data
                _products.value = response.map { product ->
                    product.copy(
                        image = "https://raw.githubusercontent.com/HamraJekeen/ProductDetails/main/${product.image}"
                    )
                }
            } catch (e: Exception) {
                // Handle errors (e.g., log, show fallback message, etc.)
                _products.value = emptyList() // Empty list in case of failure
            }
        }
    }


    private val _searchedProduct = mutableStateOf<ProductDetail?>(null)
    val searchedProduct: State<ProductDetail?> get() = _searchedProduct

    // Function to fetch a specific product by title
    fun fetchProductByTitle(title: String) {
        viewModelScope.launch {
            try {
                // Fetch all products from the API
                val products = RetrofitInstance.service.getProducts()

                // Search for the product with the matching title
                val product = products.find { it.title.equals(title, ignoreCase = true) }

                val updatedProduct = product?.copy(
                    image = "https://raw.githubusercontent.com/HamraJekeen/ProductDetails/main/${product.image}"
                )
                _searchedProduct.value = updatedProduct

            } catch (e: Exception) {
                // Handle errors
                _searchedProduct.value = null
            }
        }
    }
}
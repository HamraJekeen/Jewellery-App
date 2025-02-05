import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.euphoria_colombo.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    // StateFlow to hold the cart items
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> get() = _cartItems

    // Function to add a product to the cart
    fun addToCart(product: Product, quantity: Int = 1) {
        viewModelScope.launch {
            val existingItem = _cartItems.value.find { it.product.id == product.id }
            if (existingItem != null) {
                // If the product is already in the cart, update the quantity
                val updatedItems = _cartItems.value.map {
                    if (it.product.id == product.id) {
                        it.copy(quantity = it.quantity + quantity)
                    } else {
                        it
                    }
                }
                _cartItems.value = updatedItems
            } else {
                // If the product is not in the cart, add it
                _cartItems.value = _cartItems.value + CartItem(product, quantity)
            }
        }
    }

    // Function to remove a product from the cart
    fun removeFromCart(product: Product) {
        viewModelScope.launch {
            _cartItems.value = _cartItems.value.filter { it.product.id != product.id }
        }
    }

    // Function to update the quantity of a product in the cart
    fun updateQuantity(product: Product, quantity: Int) {
        viewModelScope.launch {
            val updatedItems = _cartItems.value.map {
                if (it.product.id == product.id) {
                    it.copy(quantity = quantity)
                } else {
                    it
                }
            }
            _cartItems.value = updatedItems
        }
    }

    // Data class to represent an item in the cart
    data class CartItem(
        val product: Product,
        val quantity: Int
    )
}

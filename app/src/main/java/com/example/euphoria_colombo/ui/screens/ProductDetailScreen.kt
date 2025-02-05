package com.example.euphoria_colombo.ui.screens

import CartViewModel
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.euphoria_colombo.api.ProductApi
import com.example.euphoria_colombo.api.ProductServiceBuilder
import com.example.euphoria_colombo.model.Product
import com.example.euphoria_colombo.model.ProductDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.euphoria_colombo.R
import com.example.euphoria_colombo.model.Category
//import com.example.euphoria_colombo.api.RetrofitInstance.ProductDetailApi
import com.example.euphoria_colombo.ui.ProductViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

import java.net.URL


@Composable
fun ProductDetail(productName: String?,
                        viewModel: ProductViewModel,
                        navController: NavController,
                        onAddToCartClicked: () -> Unit,
                  cartViewModel: CartViewModel
) {
    val context = LocalContext.current

    var quantity by remember { mutableStateOf(1) }
    // Use the passed product name to fetch the product directly
    var showProductNotFound by remember { mutableStateOf(false) }

    // Use the passed product name to fetch the product directly
    var noInternet by remember { mutableStateOf(false) }

    // Check network connectivity
    fun isInternetAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    LaunchedEffect(productName) {
        noInternet = !isInternetAvailable()
        if (!noInternet) {
            viewModel.fetchProductByTitle(productName ?: "")
            showProductNotFound = false
            delay(6000)
            if (viewModel.searchedProduct.value == null) {
                showProductNotFound = true
            }
        }
    }


    val searchedProduct = viewModel.searchedProduct.value // Access searched product state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Back Button and Product Title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Back button
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back Button",
                modifier = Modifier
                    .size(34.dp)
                    .padding(bottom = 0.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Product Details Title
            Text(
                text = stringResource(R.string.product_details),

                style = MaterialTheme.typography.headlineSmall
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (searchedProduct != null) {
            // Product image
            AsyncImage(
                model = searchedProduct.image,
                contentDescription = "Product Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

// Product title
            Text(" ${searchedProduct.title}",
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

// Product price
            Text(
                " ${searchedProduct.price}",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(20.dp))

//product description
            Text(
                "Description: ${searchedProduct.description}",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(20.dp))

// Box for the quantity changer and Add to Cart
            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(0.dp)
                    )
                    .background(Color.White)
                    .padding(0.dp)
            ) {

                // Quantity Increaser/Decreaser
                Row(
                    modifier = Modifier
                        .padding(vertical = 0.dp)

                        .align(alignment = Alignment.Center),
                    horizontalArrangement = Arrangement.Center,

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Decrease Quantity Button
                    TextButton(onClick = {
                        if (quantity > 1) quantity -= 1
                    }) {
                        Text("-",
//                        fontSize = 24.sp,
                            style = MaterialTheme.typography.titleSmall,
                            color =MaterialTheme.colorScheme.scrim)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Quantity Display
                    Text(
                        text = quantity.toString(),

                        style = MaterialTheme.typography.titleMedium,
                        color =MaterialTheme.colorScheme.scrim
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // Increase Quantity Button
                    TextButton(onClick = { quantity += 1 }) {
                        Text("+",
                            style = MaterialTheme.typography.titleSmall,
                            color =MaterialTheme.colorScheme.scrim)
                    }
                }

            }
            Button(
                onClick = {
                    if (searchedProduct != null) {
                        val product = searchedProduct.toProduct() // Convert to Product
                        cartViewModel.addToCart(product, quantity) // Add to cart
                        Toast.makeText(context, context.getString(R.string.add_cart), Toast.LENGTH_SHORT).show()
                    }
                },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surfaceContainer),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.add_to_cart),
                    color = Color.White
                )
            }
        }else if(!isInternetAvailable()){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Internet Connection.",
                    color = Color.Red,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }

        }
        else if(showProductNotFound){
            Spacer(modifier = Modifier.height(36.dp))

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Product not found.",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
            //Text("Product not found.")
        }

        Spacer(modifier = Modifier.height(16.dp))


    }
}
fun ProductDetail.toProduct(): Product {
    return Product(
        id = this.id,
        name = this.title, // Mapping title to name
        slug = "", // Assign a default value if `ProductDetail` doesn't have it
        image = listOf(this.image), // Convert single image URL to a list
        description = this.description,
        price = this.price,
        is_active = 1, // Defaulting to active (adjust as needed)
        is_featured = 0, // Default value
        in_stock = 1, // Default value (adjust according to logic)
        on_sale = 0, // Default value (change if necessary)
        category = Category( // Provide a default category if `ProductDetail` lacks it
            id = 0,
            name = "Default Category",
            slug = "default-category",
            image = "",
            is_active = 1,
            created_at = "",
            updated_at = ""
        ),
        created_at = "", // Assign a default timestamp
        updated_at = ""  // Assign a default timestamp
    )
}





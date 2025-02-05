package com.example.euphoria_colombo.ui.screens

import CartViewModel
import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.euphoria_colombo.R
import com.example.euphoria_colombo.api.ProductApi
import com.example.euphoria_colombo.api.ProductServiceBuilder
import com.example.euphoria_colombo.data.ProductDataSource
import com.example.euphoria_colombo.model.Datasource
import com.example.euphoria_colombo.model.Product
import com.example.euphoria_colombo.model.ProductResponse

import com.example.euphoria_colombo.ui.theme.primaryContainerLightMediumContrast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun  ChainsScreen(navController: NavController,viewModel: CartViewModel){
    val configuration = LocalConfiguration.current

    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            // Landscape layout
            ChainScreenLandscape(navController,viewModel)
        }
        else -> {
            // Portrait layout
            ChainScreenPortrait(navController,viewModel)
        }
    }
}

@Composable
fun ChainScreenPortrait(navController: NavController,viewModel: CartViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
    ) {

        Box(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()

            ) {

                ChainProducts(navController = navController,viewModel)


            }
        }
    }
}
@Composable
fun ChainScreenLandscape(navController: NavController,viewModel: CartViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
    ) {
        Box(modifier = Modifier.weight(1f)){
            Column(
                modifier = Modifier
                    .fillMaxWidth()

            ) {

                ChainProductsLandscape( navController = navController,viewModel)


            }
        }


    }
}
@Composable
fun ChainProducts(
    navController: NavController,
    viewModel: CartViewModel
) {
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var currentPage by remember { mutableStateOf(1) }
    var hasMorePages by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val productDataSource = ProductDataSource(context)

    // Check for internet connectivity
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetworkInfo

    // Function to load products
    fun loadProducts(page: Int) {
        if (isLoading) return

        isLoading = true

        if (activeNetwork != null && activeNetwork.isConnected) {
            // Fetch products from API using ProductServiceBuilder
            ProductServiceBuilder.buildService(ProductApi::class.java).getProducts(page).enqueue(object : Callback<ProductResponse> {
                override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                    if (response.isSuccessful) {
                        val newProducts = response.body()?.data?.filter { product ->
                            product.category.slug.equals("chains", ignoreCase = true)
                        } ?: emptyList()


                        products = if (page == 1) newProducts else products + newProducts
                        hasMorePages = response.body()?.links?.next != null
                    } else {
                        Toast.makeText(context, "Failed to load products", Toast.LENGTH_SHORT).show()
                    }
                    isLoading = false
                }

                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                    Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    isLoading = false
                }
            })
        } else {
            // Load products from local JSON file using the new data source
            products = productDataSource.loadProductsFromJson()
            isLoading = false
        }
    }

    // Load initial products
    LaunchedEffect(currentPage) {
        loadProducts(currentPage)
    }

    if (isLoading && products.isEmpty()) {
        // Show loading indicator
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Loading...", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        // Display products
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
        ) {
            // Title with decorative lines
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left line
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .padding(start = 40.dp)
                        .height(3.dp)
                        .background(MaterialTheme.colorScheme.surfaceDim) // Decorative line
                )

                // Title text
                Text(
                    text = stringResource(R.string.ChainProducts), // Change to your desired title
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .wrapContentWidth()
                )

                // Right line
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .padding(end = 40.dp)
                        .height(3.dp)
                        .background(MaterialTheme.colorScheme.surfaceDim) // Decorative line
                )
            }

            // LazyColumn to display products in rows of two
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                // Use items() to lazily load each chunk (row) of 2 products
                items(products.chunked(2)) { rowProducts ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        rowProducts.forEach { product ->
                            ProductItem(
                                title = product.name,
                                price = "Price: ${product.price}",
                                imageRes = product.image.first(), // Assuming you have a way to convert URL to resource
                                onAddToCartClicked = {
                                    // Handle add to cart
                                    viewModel.addToCart(product)
                                },
                                onImageClicked = {
                                    navController.navigate("productMaster/${product.name}")
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp)) // Space between rows
                }

                // Load more products when reaching the end of the list
                if (hasMorePages && !isLoading) {
                    item {
                        LaunchedEffect(Unit) {
                            currentPage + 1
                            loadProducts(currentPage)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ChainProductsLandscape(navController: NavController,
                           viewModel: CartViewModel) {
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var currentPage by remember { mutableStateOf(1) }
    var hasMorePages by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val productDataSource = ProductDataSource(context)

    // Check for internet connectivity
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetworkInfo

    // Function to load products
    fun loadProducts(page: Int) {
        if (isLoading) return

        isLoading = true

        if (activeNetwork != null && activeNetwork.isConnected) {
            // Fetch products from API using ProductServiceBuilder
            ProductServiceBuilder.buildService(ProductApi::class.java).getProducts(page).enqueue(object : Callback<ProductResponse> {
                override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                    if (response.isSuccessful) {
                        val newProducts = response.body()?.data?.filter { product ->
                            product.category.slug.equals("chains", ignoreCase = true)
                        } ?: emptyList()

                        products = if (page == 1) newProducts else products + newProducts
                        hasMorePages = response.body()?.links?.next != null
                    } else {
                        Toast.makeText(context, "Failed to load products", Toast.LENGTH_SHORT).show()
                    }
                    isLoading = false
                }

                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                    Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    isLoading = false
                }
            })
        } else {
            // Load products from local JSON file using the new data source
            products = productDataSource.loadProductsFromJson()
            isLoading = false
        }
    }

    // Load initial products
    LaunchedEffect(currentPage) {
        loadProducts(currentPage)
    }

    if (isLoading && products.isEmpty()) {
        // Show loading indicator
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Loading...", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        // Display products
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
        ) {
            // Title with decorative lines
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left line
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .padding(start = 40.dp)
                        .height(3.dp)
                        .background(MaterialTheme.colorScheme.surfaceDim) // Decorative line
                )

                // Title text
                Text(
                    text = stringResource(R.string.ChainProducts), // Change to your desired title
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .wrapContentWidth()
                )

                // Right line
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .padding(end = 40.dp)
                        .height(3.dp)
                        .background(MaterialTheme.colorScheme.surfaceDim) // Decorative line
                )
            }

            // LazyColumn to display products in rows of two
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                // Use items() to lazily load each chunk (row) of 2 products
                items(products.chunked(4)) { rowProducts ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        rowProducts.forEach { product ->
                            ProductItem(
                                title = product.name,
                                price = "Price: ${product.price}",
                                imageRes = product.image.first(), // Assuming you have a way to convert URL to resource
                                onAddToCartClicked = {
                                    // Handle add to cart
                                    viewModel.addToCart(product)
                                },
                                onImageClicked = {
                                    navController.navigate("productMaster/${product.name}")
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp)) // Space between rows
                }

                // Load more products when reaching the end of the list
                if (hasMorePages && !isLoading) {
                    item {
                        LaunchedEffect(Unit) {
                            currentPage += 1
                            loadProducts(currentPage)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ProductItem(title: String, price: String, imageRes: String, onAddToCartClicked: () -> Unit, onImageClicked: () -> Unit) {
    val context = LocalContext.current
    val imageResId = imageRes.toIntOrNull() ?: R.drawable.photo // Use a default image if conversion fails

    Column(
        modifier = Modifier
            .width(200.dp) // Set a fixed width for the item
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = if (imageResId != R.drawable.photo) {
                    rememberImagePainter(imageResId) // For local drawable
                } else {
                    rememberImagePainter(imageRes) // For remote URL
                },
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onImageClicked() } // Handle image click
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = price,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(0.dp))
        Button(
            onClick = {
                onAddToCartClicked()
                Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.cart_add), style = MaterialTheme.typography.labelMedium)
        }
    }
}
package com.example.euphoria_colombo.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.euphoria_colombo.BottomNavigationBar
import com.example.euphoria_colombo.R
import com.example.euphoria_colombo.Screen
import com.example.euphoria_colombo.TopBar


@Composable
fun CartScreen(navController: NavHostController) {
    val configuration = LocalConfiguration.current

    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {

            CartScreenLandscape(navController)
        }
        else -> {

            CartScreenPortrait(navController)
        }
    }
}
@Composable
fun CartScreenPortrait(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
    ) {

        Box(modifier = Modifier
            .weight(1f)
            .padding(top = 20.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                CartContent(navController)

            }
        }

    }
}
@Composable
fun CartScreenLandscape(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
    ) {

        Box(modifier = Modifier
            .weight(1f)
            .padding(top = 20.dp)){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                CartContent(navController)

            }
        }


    }
}

@Composable
fun CartContent(navController: NavHostController) {
    // State to manage the subtotal
    var subtotal by remember { mutableStateOf(1500f) } // Initial subtotal

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
    ) {

        Text(
            text = stringResource(R.string.your_shopping_cart),

            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 26.dp, start = 30.dp)
        )


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(0.dp)
                )
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(0.dp)
        ) {
            Column {
                // Cart items
                CartItem(
                    productName = stringResource(R.string.earring2_title),
                    productPrice = stringResource(R.string.earring2_price),
                    imageResource = R.drawable.earring2,
                    onQuantityChange = { priceDifference ->
                        subtotal += priceDifference // Update subtotal
                    }
                )
                CartItem(
                    productName = stringResource(R.string.chain1_title),
                    productPrice = stringResource(R.string.chain1_price),
                    imageResource = R.drawable.chain1,
                    onQuantityChange = { priceDifference ->
                        subtotal += priceDifference // Update subtotal
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        CartTotals(
            subtotal = stringResource(R.string.sub_total).format(subtotal),
            delivery = stringResource(R.string.delivery),
            total = stringResource(R.string.sub_total).format(subtotal + 500f),
            navController = navController// Delivery fee added to subtotal
        )
    }
}

@Composable
fun CartItem(
    productName: String,
    productPrice: String,
    imageResource: Int,
    onQuantityChange: (Float) -> Unit
) {

    val pricePerUnit = productPrice.removePrefix("LKR ").toFloat()


    var quantity by remember { mutableStateOf(1) }


    val totalPrice = pricePerUnit * quantity

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

    ) {
        IconButton(onClick = { /* Remove item from cart */ }) {
            Icon(Icons.Default.Close, contentDescription = stringResource(R.string.remove_item))
        }

        Image(
            painter = painterResource(imageResource),
            contentDescription = productName,
            modifier = Modifier.size(65.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
        ) {
            Text(text = productName,
                style = MaterialTheme.typography.labelSmall,

                )
            Text(
                text = productPrice,

                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
        }

        Box(
            modifier = Modifier
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(0.dp)
                )
                .background(Color.White)

                .padding(0.dp)
        ) {
            Row(
                modifier = Modifier.padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Decrease quantity button
                IconButton(onClick = {
                    if (quantity > 1) {
                        quantity-- // Decrease quantity
                        onQuantityChange(-pricePerUnit)
                    }
                }) {
                    Icon(Icons.Default.Remove, contentDescription = "Decrease Quantity", tint = MaterialTheme.colorScheme.scrim)
                }

                // Display current quantity
                Text(text = "$quantity", color = MaterialTheme.colorScheme.scrim)

                // Increase quantity button
                IconButton(onClick = {
                    quantity++ // Increase quantity
                    onQuantityChange(pricePerUnit) // Increase subtotal
                }) {
                    Icon(Icons.Default.Add, contentDescription = "Increase Quantity",tint = MaterialTheme.colorScheme.scrim)
                }
            }
        }
    }
}

@Composable
fun CartTotals(subtotal: String, delivery: String, total: String,navController: NavHostController) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(R.string.subtotal),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray)
                Text(text = subtotal,
//                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray)
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(R.string.delivery_name),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray)
                Text(text = delivery, fontSize = 16.sp, color = Color.Gray)
            }


            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.total),

                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
                Text(
                    text = total,

                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
            }
            Button(
                onClick = { navController.navigate(Screen.Checkout.route)},
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surfaceContainer),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = stringResource(R.string.proceed_to_checkout), color = Color.White)
            }
        }
    }
}

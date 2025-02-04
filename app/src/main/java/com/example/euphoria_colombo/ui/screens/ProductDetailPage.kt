package com.example.euphoria_colombo.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.solver.state.State
import androidx.navigation.NavController
import com.example.euphoria_colombo.R
import com.example.euphoria_colombo.model.Picture


@Composable
fun ProductDetailScreen(
    navController: NavController,
    titleResId: Int,
    priceResId: Int,
    onAddToCartClicked: () -> Unit,
    imageResId: Int
) {
    val context = LocalContext.current
    val productTitle = stringResource(id = titleResId)
    val productPrice = stringResource(id = priceResId)
    var quantity by remember { mutableStateOf(1) }

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

        // Product image
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Product title
        Text(
            text = productTitle,
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Product price
        Text(
            text = productPrice,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(20.dp))

       //product description
        Text(
            text = stringResource(R.string.product_description),
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
                onAddToCartClicked()
                Toast.makeText(context, context.getString(R.string.add_cart), Toast.LENGTH_SHORT).show() },
            shape = RectangleShape,

            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surfaceContainer),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = stringResource(R.string.add_to_cart),
                color = Color.White)
        }
    }
}
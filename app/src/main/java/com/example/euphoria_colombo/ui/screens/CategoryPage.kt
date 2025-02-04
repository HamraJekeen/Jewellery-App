package com.example.euphoria_colombo.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.euphoria_colombo.Screen

import com.example.euphoria_colombo.data.CategoryData

@Composable
fun CategoryScreen(navController: NavHostController) {
    val configuration = LocalConfiguration.current

    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            CategoryScreenLandscape(navController)
        }
        else -> {
            CategoryScreenPortrait(navController)
        }
    }
}


@Composable
fun CategoryScreenPortrait(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
    ) {

        Box(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                CategoryContent(navController)
            }
        }

    }
}

@Composable
fun CategoryScreenLandscape(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
    ) {

        Box(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                CategoryContentLandscape(navController)
            }
        }
    }
}
@Composable
fun CategoryContent(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .size(650.dp)
        ) {
            CategoryData.categories.forEach { category ->
                CategoryProduct(category.name, category.imageResource, navController)
            }
        }
    }
}

@Composable
fun CategoryContentLandscape(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .align(Alignment.Center)
                .height(180.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryData.categories.forEach { category ->
                CategoryProduct(category.name, category.imageResource, navController)
            }
        }
    }
}

@Composable
fun CategoryProduct(categoryName: String, imageResource: Int, navController: NavController) {
    Card(
        modifier = Modifier
            .size(200.dp)

            .clickable {
                // Navigate based on category
                when (categoryName) {
                    "Chains" -> navController.navigate(Screen.Chains.route)
                    "Earrings" -> navController.navigate(Screen.Earrings.route)
                    "Rings" -> navController.navigate(Screen.Rings.route)
                }
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.outline),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(imageResource),
                contentDescription = categoryName,
                modifier = Modifier.size(120.dp)
            )
            Text(
                text = categoryName,

                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.scrim,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}



package com.example.euphoria_colombo.ui.screens

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavHostController
import com.example.euphoria_colombo.BottomNavigationBar
import com.example.euphoria_colombo.R
import com.example.euphoria_colombo.Screen
import com.example.euphoria_colombo.TopBar
import com.example.euphoria_colombo.model.Datasource

import com.example.euphoria_colombo.ui.theme.primaryContainerLightMediumContrast
import com.example.euphoria_colombo.ui.theme.surfaceContainerLight
import kotlinx.coroutines.delay
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.example.euphoria_colombo.ui.component.AboutUsTwoImagesSection
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.MutableState
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale
//import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Close
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.euphoria_colombo.ui.CouponViewModel
import com.example.euphoria_colombo.ui.component.ProductItems

data class Review(
    val name: String,
    val rating: Int,
    val review: String,
    val timestamp: String = SimpleDateFormat("dd MMM yyyy, HH:mm:ss.SSS", Locale.getDefault())
        .format(System.currentTimeMillis()),
    val id: Long = System.currentTimeMillis()
)

private fun parseReview(reviewText: String): Review? {
    try {
        val lines = reviewText.trim().lines()
        val name = lines[0].removePrefix("Name: ")
        val rating = lines[1].count { it == '⭐' }
        val review = lines[2].removePrefix("Review: ")
        val timestamp = lines[3].removePrefix("Time: ")
        return Review(name, rating, review, timestamp)
    } catch (e: Exception) {
        return null
    }
}

@Composable
fun HomePage(
    navController: NavHostController,
    viewModel: CouponViewModel = viewModel()
) {
    // Show coupon when entering HomePage
    LaunchedEffect(Unit) {
        viewModel.setShowCoupon(true)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Your existing HomePage content
        HomeScreen(navController)

        // Overlay the coupon
        CouponOverlay(
            viewModel = viewModel,
            onDismiss = {
                // Handle any additional logic after coupon is dismissed
            }
        )
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    val configuration = LocalConfiguration.current

    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {

            HomeScreenLandscape(navController)
        }
        else -> {

            HomeScreenPortrait(navController)
        }
    }
}
@Composable
fun HomeScreenPortrait(navController: NavHostController) {
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
                HeroSection(navController)
                Iconlist()
                CategorySection(navController)
                AboutUsSection(navController)
                AboutUsTwoImagesSection()
                //TopSellingProducts(datasource = Datasource(), navController = navController)
                ReviewSection(navController)
            }
        }
    }
}
@Composable
fun HomeScreenLandscape(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
    ) {

        Box(modifier = Modifier.weight(1f)){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                HeroSection(navController)
                Iconlist()
                CategorySection(navController)
                AboutUsCombinedSection(navController)
                //TopSellingProductsLandscape(datasource = Datasource(), navController = navController)
                ReviewSection(navController)
            }
        }



    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun HeroSection(navController: NavHostController) {
    val imageRes = listOf(
        R.drawable.home3,
        R.drawable.home5,
        R.drawable.home4
    )
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    // Start the automatic slider
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000) // Delay for 3 seconds
            val nextPage = (pagerState.currentPage + 1) % imageRes.size
            pagerState.animateScrollToPage(nextPage)
        }
    }
    Box(modifier = Modifier.fillMaxWidth()){
        Column {
            HorizontalPager(
                count = imageRes.size,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) { page ->
                Image(
                    painter = painterResource(id = imageRes[page]),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )

                Box(
                    modifier = Modifier
                        .padding(top = 0.dp)
                        .height(200.dp)
                        .fillMaxWidth()
                ) {

                    Text(text = stringResource(R.string.be_spoilt_for_choice),
                        modifier = Modifier.padding(top = 100.dp),

                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White)
                    Button(
                        onClick = {  navController.navigate(Screen.Category.route)},
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(50.dp, 0.dp, 0.dp, 8.dp)
                            .width(160.dp),

                        colors = ButtonDefaults.buttonColors(
                            MaterialTheme.colorScheme.surfaceContainer,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(stringResource(R.string.shop_now))
                    }

                }
            }
        }
    }


}

@Composable
fun Iconlist() {
    val configuration = LocalConfiguration.current

    val imageModifier = Modifier
        .fillMaxSize()
        .height(50.dp)
        .padding(0.dp, 0.dp, 0.dp, 0.dp)



    Column(modifier = imageModifier
        .fillMaxSize()
        .background(color = Color.White)) {
        Image(
            painter = painterResource(id = R.drawable.iconlist),
            contentDescription = stringResource(R.string.logo_image),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun CategorySection(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    if (isPortrait) {
        // Portrait mode: display items in a column with full width
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            CategoryItem(
                navController = navController,
                title = stringResource(R.string.shop_chains),
                imageRes = R.drawable.chain,
                destination = Screen.Chains.route,
                modifier = Modifier.fillMaxWidth()
            )
            CategoryItem(
                navController = navController,
                title = stringResource(R.string.shop_rings),
                imageRes = R.drawable.rings,
                destination = Screen.Rings.route,
                modifier = Modifier.fillMaxWidth()
            )
            CategoryItem(
                navController = navController,
                title = stringResource(R.string.shop_earrings),
                imageRes = R.drawable.earrings,
                destination = Screen.Earrings.route,
                modifier = Modifier.fillMaxWidth()
            )
        }
    } else {
        // Landscape mode: display items in a row with equal weights
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CategoryItem(
                navController = navController,
                title = stringResource(R.string.shop_chains),
                imageRes = R.drawable.chain,
                destination = Screen.Chains.route,
                modifier = Modifier.weight(1f)
            )
            CategoryItem(
                navController = navController,
                title = stringResource(R.string.shop_rings),
                imageRes = R.drawable.rings,
                destination = Screen.Rings.route,
                modifier = Modifier.weight(1f)
            )
            CategoryItem(
                navController = navController,
                title = stringResource(R.string.shop_earrings),
                imageRes = R.drawable.earrings,
                destination = Screen.Earrings.route,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun CategoryItem(
    navController: NavHostController,
    title: String,
    imageRes: Int,
    destination: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(150.dp)
            .padding(4.dp)
            .clickable {
                navController.navigate(destination)
            }
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Semi-transparent overlay for better text readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                //.background(Color.Black.copy(alpha = 0.3f))
        )

        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}
@Composable
fun AboutUsCombinedSection(navController: NavHostController) {
    val configuration = LocalConfiguration.current

    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                AboutUsSection(navController)
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                AboutUsTwoImagesSection()
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AboutUsSection(navController)
            AboutUsTwoImagesSection()
        }
    }
}

@Composable
fun AboutUsSection(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically){
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .padding(start = 0.dp)
                    .height(3.dp)
                    .background(MaterialTheme.colorScheme.surfaceDim) // Brown color line
            )
            Text(
                text = stringResource(R.string.learn),
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .wrapContentWidth()
            )
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .padding(end = 0.dp)
                    .height(3.dp)
                    .background(MaterialTheme.colorScheme.surfaceDim)
                // Brown color line
            )
        }
        Text(
            text = stringResource(R.string.about),

            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )


        Text(
            text = stringResource(R.string.about_text_2),

            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()

        )
        Button(
            onClick = {navController.navigate(Screen.About.route) },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp)
                .width(160.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surfaceContainer
                ,
                contentColor = Color.White // Button text color
            ),
            shape = RoundedCornerShape(12.dp)

        ) {
            Text(stringResource(R.string.about_us),
            )
        }
    }
}

//@Composable
//fun TopSellingProducts(
//    datasource: Datasource = Datasource(),
//    navController: NavController
//) {
//    val pictures = datasource.Topselling()
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(0.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 8.dp),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Left line
//            Box(
//                modifier = Modifier
//                    .width(100.dp)
//                    .padding(start = 40.dp)
//                    .height(3.dp)
//                    .background(MaterialTheme.colorScheme.surfaceDim) // Brown color line
//            )
//
//            // Text in the center
//            Text(
//                text = stringResource(R.string.top_selling_products),
//
//                style = MaterialTheme.typography.titleMedium,
//                textAlign = TextAlign.Center,
//                modifier = Modifier
//                    .padding(horizontal = 8.dp)
//                    .wrapContentWidth()
//            )
//
//            // Right line
//            Box(
//                modifier = Modifier
//                    .width(100.dp)
//                    .padding(end = 40.dp)
//                    .height(3.dp)
//                    .background(MaterialTheme.colorScheme.surfaceDim)
//                // Brown color line
//            )
//        }
//
//        // Display products in rows
//        pictures.chunked(2).forEach { rowPictures ->
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                rowPictures.forEach { picture ->
//                    ProductItems(
//                        title = stringResource(picture.titleResId),
//                        price = stringResource(picture.priceResId),
//                        imageRes = picture.imageResId,
//                        onAddToCartClicked = {
//                            //
//                        },
//                        onImageClicked = {
//                            // Navigate to product detail screen when image is clicked
//                            //navController.navigate("productDetail/${picture.titleResId}/${picture.priceResId}/${picture.imageResId}")
//
//
//                        }
//                    )
//                }
//            }
//        }
//    }
//}
//@Composable
//fun TopSellingProductsLandscape(datasource: Datasource = Datasource(),
//                                navController: NavController) {
//    val pictures = datasource.Topselling()
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(0.dp)
//    ) {Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(bottom = 8.dp),
//        horizontalArrangement = Arrangement.Center,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        // Left line
//        Box(
//            modifier = Modifier
//                .width(100.dp)
//                .padding(start = 40.dp)
//                .height(3.dp)
//                .background(MaterialTheme.colorScheme.surfaceDim)
//        )
//
//        // Text in the center
//        Text(
//            text = stringResource(R.string.top_selling_products),
//
//            style = MaterialTheme.typography.titleMedium,
//            textAlign = TextAlign.Center,
//            modifier = Modifier
//                .padding(horizontal = 8.dp)
//                .wrapContentWidth()
//        )
//
//        // Right line
//        Box(
//            modifier = Modifier
//                .width(100.dp)
//                .padding(end = 40.dp)
//                .height(3.dp)
//                .background(MaterialTheme.colorScheme.surfaceDim)
//            // Brown color line
//        )
//    }
//
//        // Display products in rows
//        pictures.chunked(4).forEach { rowPictures ->
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                rowPictures.forEach { picture ->
//                    ProductItems(
//                        title = stringResource(picture.titleResId),
//                        price = stringResource(picture.priceResId),
//                        imageRes = picture.imageResId,
//                        onAddToCartClicked = {
//                            // Handle Add to Cart action
//
//                        },
//                        onImageClicked = {
//                            // Navigate to product detail screen when image is clicked
//                            //navController.navigate("productDetail/${picture.titleResId}/${picture.priceResId}/${picture.imageResId}")
//
//
//                        }
//                    )
//                }
//            }
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewSection(navController: NavHostController) {
    val reviewInput = remember { mutableStateOf("") }
    val usernameInput = remember { mutableStateOf("") }
    val rating = remember { mutableStateOf(0) }
    val displayedReviews = remember { mutableStateOf("") }
    val showReviews = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Section Title with Show/Hide Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Customer Reviews",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = {
                        showReviews.value = !showReviews.value
                        if (showReviews.value) {
                            readReviews(displayedReviews, context)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(if (showReviews.value) "Hide Reviews" else "Show Reviews")
                }
            }

            // Review Input Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                //elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Write a Review",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    OutlinedTextField(
                        value = usernameInput.value,
                        onValueChange = { usernameInput.value = it },
                        label = { Text("Your Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )

                    // Star Rating
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        for (i in 1..5) {
                            Icon(
                                imageVector = if (i <= rating.value)
                                    Icons.Filled.Star else Icons.Outlined.Star,
                                contentDescription = "Star $i",
                                modifier = Modifier
                                    .size(36.dp)
                                    .clickable { rating.value = i },
                                tint = if (i <= rating.value)
                                    MaterialTheme.colorScheme.surfaceContainer else Color.Gray
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }

                    OutlinedTextField(
                        value = reviewInput.value,
                        onValueChange = { reviewInput.value = it },
                        label = { Text("Your Review") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        minLines = 3,
                        maxLines = 5,
                        shape = RoundedCornerShape(8.dp)
                    )

                    Button(
                        onClick = {
                            if (usernameInput.value.isNotEmpty() && rating.value > 0) {
                                val timestamp = SimpleDateFormat("dd MMM yyyy, HH:mm:ss.SSS", Locale.getDefault())
                                    .format(System.currentTimeMillis())
                                val reviewWithDetails = """
                                    |Name: ${usernameInput.value}
                                    |Rating: ${"⭐".repeat(rating.value)}
                                    |Review: ${reviewInput.value}
                                    |Time: $timestamp
                                    |${"-".repeat(50)}
                                """.trimMargin()

                                val existingReviews = try {
                                    context.openFileInput("reviews.txt").bufferedReader().use { it.readText() }
                                } catch (e: IOException) {
                                    ""
                                }

                                val updatedReviews = if (existingReviews.isBlank()) {
                                    reviewWithDetails
                                } else {
                                    "$reviewWithDetails\n$existingReviews"
                                }

                                writeReview(mutableStateOf(updatedReviews), context)
                                reviewInput.value = ""
                                usernameInput.value = ""
                                rating.value = 0
                                readReviews(displayedReviews, context)
                            } else {
                                Toast.makeText(context, "Please enter your name and rating", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Submit Review")
                    }
                }
            }

            // Reviews Display Section - Only show when showReviews is true
            if (showReviews.value && displayedReviews.value.isNotEmpty() && displayedReviews.value != "No reviews yet") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Reviews",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(
                        onClick = { showReviews.value = false }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close reviews",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                val reviews = displayedReviews.value
                    .split("-".repeat(50))
                    .filter { it.isNotBlank() }
                    .mapNotNull { parseReview(it) }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    reviews.forEach { review ->
                        ReviewCard(
                            review = review,
                            onDelete = {
                                val updatedReviews = reviews.filter { it.timestamp != review.timestamp }
                                if (updatedReviews.isEmpty()) {
                                    writeReview(mutableStateOf(""), context)
                                    displayedReviews.value = "No reviews yet"
                                } else {
                                    val updatedReviewText = updatedReviews.joinToString("\n" + "-".repeat(50) + "\n") {
                                        """
                                        |Name: ${it.name}
                                        |Rating: ${"⭐".repeat(it.rating)}
                                        |Review: ${it.review}
                                        |Time: ${it.timestamp}
                                        """.trimMargin()
                                    }
                                    writeReview(mutableStateOf(updatedReviewText), context)
                                    readReviews(displayedReviews, context)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ReviewCard(
    review: Review,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = review.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = review.timestamp,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete review",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                repeat(review.rating) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.surfaceContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = review.review,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

private fun writeReview(input: MutableState<String>, context: Context) {
    try {
        // Simply write the new content, overwriting the old file
        val outputFile: FileOutputStream = context.openFileOutput("reviews.txt", Context.MODE_PRIVATE)
        outputFile.write(input.value.toByteArray())
        outputFile.close()

        if (input.value.isBlank()) {
            Toast.makeText(context, "Review deleted successfully!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Review submitted successfully!", Toast.LENGTH_SHORT).show()
        }
    } catch (e: IOException) {
        Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show()
        e.printStackTrace()
    }
}

private fun readReviews(output: MutableState<String>, context: Context) {
    try {
        val inputFile: FileInputStream = context.openFileInput("reviews.txt")
        val content = inputFile.bufferedReader().use { it.readText() }
        inputFile.close()

        // Only update if there's actual content
        output.value = if (content.isBlank()) "No reviews yet" else content
    } catch (e: IOException) {
        output.value = "No reviews yet"
        e.printStackTrace()
    }
}


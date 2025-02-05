package com.example.euphoria_colombo


//import CheckOutScreen
//import com.example.euphoria_colombo.ui.screens.CheckOutScreen
//import com.example.euphoria_colombo.ui.screens.CheckoutPage

import CartViewModel
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.BatteryManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.euphoria_colombo.ui.AuthViewModel
import com.example.euphoria_colombo.ui.screens.AboutScreen
import com.example.euphoria_colombo.ui.screens.CartScreen
import com.example.euphoria_colombo.ui.screens.CategoryScreen
import com.example.euphoria_colombo.ui.screens.ChainsScreen
import com.example.euphoria_colombo.ui.screens.CheckOutScreen
//import com.example.euphoria_colombo.ui.screens.CouponScreen
import com.example.euphoria_colombo.ui.screens.EarringsScreen
import com.example.euphoria_colombo.ui.screens.HomeScreen
import com.example.euphoria_colombo.ui.screens.LoginScreen
import com.example.euphoria_colombo.ui.screens.ProductDetailScreen
import com.example.euphoria_colombo.ui.screens.RingsScreen
import com.example.euphoria_colombo.ui.screens.SignUpScreen
import com.example.euphoria_colombo.ui.screens.SplashScreen
import com.example.euphoria_colombo.ui.screens.UserProfileScreen
import com.example.euphoria_colombo.ui.theme.Euphoria_ColomboTheme
import androidx.activity.viewModels
//import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.Observer
import com.example.euphoria_colombo.ui.AuthState
import com.example.euphoria_colombo.ui.screens.HomePage
import com.example.euphoria_colombo.ui.screens.ProductDetail

//import com.example.euphoria_colombo.ui.AuthViewModel



class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Euphoria_ColomboTheme {
                val navController = rememberNavController()
                //val authViewModel = viewModel<AuthViewModel>()

                var showSplashScreen by remember { mutableStateOf(true) }

                if (showSplashScreen) {
                    SplashScreen(
                        logo = painterResource(id = R.drawable.logo),
                        navigateToMain = {
                            showSplashScreen = false
                        }
                    )

                } else {

                    MainScreen(navController, authViewModel = authViewModel, cartViewModel)
                }



            }
        }
    }
}
enum class Screen(val route: String) {
    Home("home_screen"),
    Category("category_screen"),
    Cart("cart_screen"),
    Account("account_screen"),
    Register("register_screen"),
    Chains("chains"),
    Earrings("earrings"),
    Rings("rings"),
    About("AboutScreen"),
    ProductDetail("productDetail/{titleResId}/{priceResId}/{imageResId}"),
    Checkout("checkout_screen"),
    profile("profile_screen"),
    productMaster("productMaster/{productName}")
}


@Composable
fun MainScreen(navController: NavHostController,authViewModel: AuthViewModel,cartViewModel: CartViewModel) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry.value?.destination?.route

    val isSplashScreen = currentDestination == Screen.Account.route

    val isHomeScreen = currentDestination == Screen.Home.route

    Scaffold(

        topBar = {

            // Show top bar only for certain screens

                // Show BatteryStatusBar only on non-splash screens

                // Show the regular TopBar for certain screens
                if (currentDestination != Screen.Account.route &&
                    currentDestination != Screen.Register.route &&
                    currentDestination != Screen.ProductDetail.route &&
                    currentDestination != Screen.productMaster.route &&
                    currentDestination != Screen.Checkout.route
                ) {
                    TopBar(navController, isHomeScreen)
                }

        },
        bottomBar = {
            // Show bottom bar only for certain screens
            if (currentDestination != Screen.Account.route &&
                currentDestination != Screen.Register.route &&
                currentDestination != Screen.ProductDetail.route&&
                currentDestination != Screen.Chains.route&&
                currentDestination != Screen.Rings.route&&
                currentDestination != Screen.Earrings.route&&
                currentDestination != Screen.About.route&&
                currentDestination != Screen.productMaster.route &&
                currentDestination != Screen.Checkout.route
            ) {
                BottomNavigationBar(navController)
            }
        }
    ) { paddingValues ->
        NavHostContainer(navController, Modifier.padding(paddingValues),authViewModel,cartViewModel)
    }
}
@Composable
fun NavHostContainer(navController: NavHostController, modifier: Modifier = Modifier, authViewModel: AuthViewModel,cartViewModel: CartViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screen.Account.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) { HomePage(navController) }
        composable(Screen.About.route) { AboutScreen(navController) }
        composable(Screen.Category.route) { CategoryScreen(navController) }
        composable(Screen.Cart.route) { CartScreen(navController,cartViewModel) }
        composable(Screen.Account.route) { LoginScreen(navController,authViewModel) }
        composable(Screen.Register.route) { SignUpScreen(navController,authViewModel) }
        composable(Screen.Chains.route) { ChainsScreen(navController,cartViewModel) }
        composable(Screen.Earrings.route) { EarringsScreen(navController,cartViewModel) }
        composable(Screen.Rings.route) { RingsScreen(navController,cartViewModel) }
        composable(
            Screen.ProductDetail.route,
            arguments = listOf(
                navArgument("titleResId") { type = NavType.StringType },
                navArgument("priceResId") { type = NavType.StringType },
                navArgument("imageResId") { type = NavType.StringType }
            ),
            enterTransition = {
                fadeIn(animationSpec = tween(1000)) + slideIntoContainer(
                    animationSpec = tween(1000),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(1000)) + slideOutOfContainer(
                    animationSpec = tween(1000),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) { backStackEntry ->
            val titleResId = backStackEntry.arguments?.getString("titleResId")?.toInt() ?: 0
            val priceResId = backStackEntry.arguments?.getString("priceResId")?.toInt() ?: 0
            val imageResId = backStackEntry.arguments?.getString("imageResId")?.toInt() ?: 0
            ProductDetailScreen(titleResId = titleResId, priceResId = priceResId, imageResId = imageResId, navController=navController,onAddToCartClicked = { // Add to Cart
            })
        }
        // Add the checkout screen composable
        composable(Screen.Checkout.route,
            enterTransition = {
                fadeIn(animationSpec = tween(1000)) + slideIntoContainer(
                    animationSpec = tween(1000),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(1000)) + slideOutOfContainer(
                    animationSpec = tween(1000),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) { CheckOutScreen(navController) }
        composable(Screen.profile.route) { UserProfileScreen(navController, authViewModel) }
        composable(
            Screen.productMaster.route,
            arguments = listOf(
                navArgument("productName") { type = NavType.StringType },

            ),
            enterTransition = {
                fadeIn(animationSpec = tween(1000)) + slideIntoContainer(
                    animationSpec = tween(1000),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(1000)) + slideOutOfContainer(
                    animationSpec = tween(1000),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) { backStackEntry ->
            val productName = backStackEntry.arguments?.getString("productName")
            ProductDetail(
                productName = productName,
                viewModel = viewModel(),
                cartViewModel = cartViewModel,
                navController = navController,
                onAddToCartClicked = {
                    // Handle add to cart logic here
                }
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController, isHomeScreen: Boolean) {
    var search by rememberSaveable { mutableStateOf("") }
    Column {



        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(1.dp, 35.dp, 0.dp, 3.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Show logo on home screen or back button on other screens
            if (isHomeScreen) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo image",
                    modifier = Modifier
                        .height(58.dp)
                        .padding(bottom = 5.dp)
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back Button",
                    modifier = Modifier
                        .size(36.dp)
                        .padding(bottom = 10.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                )
            }

            Spacer(modifier = Modifier.weight(0.5f))


            TextField(
                value = search,
                onValueChange = { search = it },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.search),
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                colors = textFieldColors(

                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = Color.Transparent,

                    ),
                modifier = Modifier
                    .weight(15f)
                    .padding(bottom = 8.dp, end = 8.dp)
                    .clip(shape = MaterialTheme.shapes.small)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = MaterialTheme.shapes.small
                    )
            )


            Icon(
                painter = painterResource(id = R.drawable.ic_cart),
                contentDescription = "Cart Icon",
                modifier = Modifier
                    .size(36.dp)
                    .padding(bottom = 5.dp)
                    .clickable {
                        navController.navigate(Screen.Checkout.route)
                    },
            )
        }
        NetworkStatusDisplay(LocalContext.current)
    }
}


@Composable
fun BottomNavigationBar(navController: NavHostController, modifier: Modifier = Modifier) {
    // Track the current back stack entry
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomNavigationItem(
            iconId = R.drawable.ic_home,
            label = stringResource(R.string.home),
            isSelected = currentRoute == Screen.Home.route,
            onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        BottomNavigationItem(
            iconId = R.drawable.ic_product,
            label = stringResource(R.string.categories),
            isSelected = currentRoute == Screen.Category.route,
            onClick = {
                navController.navigate(Screen.Category.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        BottomNavigationItem(
            iconId = R.drawable.ic_shopping_carts,
            label = stringResource(R.string.cart),
            isSelected = currentRoute == Screen.Cart.route,
            onClick = {
                navController.navigate(Screen.Cart.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        BottomNavigationItem(
            iconId = R.drawable.ic_account,
            label = stringResource(R.string.account),
            isSelected = currentRoute == Screen.profile.route,
            onClick = {
                navController.navigate(Screen.profile.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

    }
}
@Composable
fun BottomNavigationItem(iconId: Int, label: String, isSelected: Boolean, onClick: () -> Unit) {
    val iconColor = if (isSelected) {
        MaterialTheme.colorScheme.onSecondaryContainer // Use primary color for selected state
    } else {
        Color.Gray // A less prominent color for unselected state
    }

    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = label,
            modifier = Modifier.size(36.dp),
            tint = iconColor
        )
        Text(
            text = label,
            color = iconColor
        )
    }

}
//@Composable
//fun BatteryLevelDisplay(context: Context) {
//    // Variable to store battery level
//    var batteryLevel by remember { mutableStateOf(0f) }
//
//    // Creating a BatteryManager to access battery data
//    val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
//
//    // Creating a BroadcastReceiver to listen for battery changes
//    val batteryReceiver = remember {
//        object : BroadcastReceiver() {
//            override fun onReceive(context: Context, intent: Intent) {
//                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
//                val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
//                if (level != -1 && scale != -1) {
//                    batteryLevel = (level / scale.toFloat()) * 100
//                }
//            }
//        }
//    }
//
//    // Register and unregister the receiver
//    DisposableEffect(Unit) {
//        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
//        context.registerReceiver(batteryReceiver, filter)
//
//        // Unregister the receiver when the composable is disposed
//        onDispose {
//            context.unregisterReceiver(batteryReceiver)
//        }
//    }
//
//    // Determine the appropriate battery icon based on battery level
//    val batteryIcon = when {
//        batteryLevel <= 20 -> R.drawable.battery_1
//        batteryLevel <= 40 -> R.drawable.battery_2
//        batteryLevel <= 60 -> R.drawable.battery_4
//        batteryLevel <= 80 -> R.drawable.battery_6
//        else -> R.drawable.battery_full
//    }
//
//    // The content to be displayed on the screen
//    Column(
//        modifier = Modifier,
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Row {
//            // Display the battery icon based on battery level
//            Image(
//                painter = painterResource(id = batteryIcon),
//                contentDescription = "Battery",
//                modifier = Modifier.size(17.dp) // Adjust size as needed
//            )
//            Text(
//                text = ": ${batteryLevel.toInt()}%",
//                style = MaterialTheme.typography.labelMedium,
//                modifier = Modifier.padding(top = 2.dp)
//
//            )
//        }
//    }
//}
//
//@Composable
//fun Battery() {
//    Column(
//        modifier = Modifier
//            .padding(top = 16.dp, bottom = 0.dp, start = 390.dp, end = 5.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Spacer(modifier = Modifier.height(14.dp))
//        // Display the battery icon with the battery level
//        BatteryLevelDisplay(context = LocalContext.current)
//    }
//}

@Composable
fun NetworkStatusDisplay(context: Context) {
    var isConnected by remember { mutableStateOf(false) }
    var connectionType by remember { mutableStateOf("No Connection") }
    var showBanner by remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }

    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    DisposableEffect(Unit) {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                val capabilities = connectivityManager.getNetworkCapabilities(network)
                capabilities?.let {
                    isConnected = true
                    showDialog = false
                    connectionType = when {
                        it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "Connected to WiFi"
                        it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Connected to Mobile Data"
                        else -> "Connected"
                    }
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                isConnected = false
                showDialog = true
                connectionType = "No Internet Connection"
                showBanner = true
            }
        }

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)

        // Initial check
        val activeNetwork = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        isConnected = capabilities != null
        if (capabilities != null) {
            connectionType = when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "Connected to WiFi"
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Connected to Mobile Data"
                else -> "Connected"
            }
        }

        onDispose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }

    if (showBanner) {
        AnimatedVisibility(
            visible = true,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (isConnected) Color(0xFF4CAF50) else Color(0xFFE57373)
                    )
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isConnected) R.drawable.ic_wifi
                        else R.drawable.ic_wifi_off
                    ),
                    contentDescription = "Network Status",
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = connectionType,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
                if (isConnected) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Dismiss",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { showBanner = false },
                        tint = Color.White
                    )
                }
            }
        }
    }

    if (showDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showDialog = false },
            containerColor = Color(0xFF424242),
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_wifi_off),
                    contentDescription = "No Internet",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            },
            title = {
                Text(
                    text = "Please check your",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "internet connection",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "and try again",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Text("OK")
                }
            }
        )
    }
}
//@Composable
//fun BatteryLevelDisplay(context: Context) {
//    // Variable to store battery level
//    var batteryLevel by remember { mutableStateOf(0f) }
//
//    // Creating a BatteryManager to access battery data
//    val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
//
//    // Creating a BroadcastReceiver to listen for battery changes
//    val batteryReceiver = remember {
//        object : BroadcastReceiver() {
//            override fun onReceive(context: Context, intent: Intent) {
//                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
//                val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
//                if (level != -1 && scale != -1) {
//                    batteryLevel = (level / scale.toFloat()) * 100
//                }
//            }
//        }
//    }
//
//    // Register and unregister the receiver
//    DisposableEffect(Unit) {
//        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
//        context.registerReceiver(batteryReceiver, filter)
//
//        // Unregister the receiver when the composable is disposed
//        onDispose {
//            context.unregisterReceiver(batteryReceiver)
//        }
//    }
//
//    // The content to be displayed on the screen
//    Column(
//        modifier = Modifier,
//            //.fillMaxSize()
//            //.padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = ": ${batteryLevel.toInt()}%",
//            style = MaterialTheme.typography.labelMedium
//        )
//    }
//}
//
//@Composable
//fun Battery() {
//    Column(
//        modifier = Modifier
//
//            .padding(top = 16.dp, bottom = 0.dp, start =390.dp, end = 5.dp ),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Spacer(modifier = Modifier.height(16.dp))
//        Row {
//            Image(
//                painter = painterResource(id = R.drawable.battery), // Replace with your image resource
//                contentDescription = "Battery",
//                modifier = Modifier.size(14.dp)
//            )
//            BatteryLevelDisplay(context = LocalContext.current)
//        }
//
//         // Display the battery status here
//    }
//}





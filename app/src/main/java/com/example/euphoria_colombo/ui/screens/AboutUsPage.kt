package com.example.euphoria_colombo.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.euphoria_colombo.BottomNavigationBar
import com.example.euphoria_colombo.R
import com.example.euphoria_colombo.Screen
import com.example.euphoria_colombo.TopBar
import com.example.euphoria_colombo.ui.component.AboutUsTwoImagesSection
import com.example.euphoria_colombo.ui.theme.primaryContainerLightMediumContrast
import com.example.euphoria_colombo.ui.theme.surfaceContainerLight
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AboutScreen(navController: NavHostController){
    val configuration = LocalConfiguration.current

    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            // Landscape layout
            AboutScreenLandscape(navController)
        }
        else -> {
            // Portrait layout
            AboutScreenPortrait(navController)
        }
    }
}
@Composable
fun AboutScreenPortrait(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Box(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                HeadSection(navController)
                Spacer(modifier = Modifier.height(16.dp))
                AboutSection()
                Spacer(modifier = Modifier.height(24.dp))
                ContactSection()
                Spacer(modifier = Modifier.height(24.dp))
                //AboutUsTwoImagesSection()
               // Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
@Composable
fun AboutScreenLandscape(navController: NavHostController) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            HeadSection(navController)
            Spacer(modifier = Modifier.height(16.dp))

            // About section
            AboutSection()
            Spacer(modifier = Modifier.height(24.dp))

            // Contact section in row layout for landscape
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Contact Information",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Phone Card
                    ContactCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Phone,
                        title = "Call Us",
                        content = "+94 77 123 4567",
                        onClick = {
                            val intent = Intent(Intent.ACTION_DIAL)
                            intent.data = Uri.parse("tel:+94771234567")
                            context.startActivity(intent)
                        }
                    )

                    // Location Card
                    ContactCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.LocationOn,
                        title = "Visit Our Store",
                        content = "123 Galle Road, Colombo 03",
                        onClick = {
                            val uri = Uri.parse("geo:6.9271,79.8612?q=Euphoria+Colombo")
                            val mapIntent = Intent(Intent.ACTION_VIEW, uri)
                            mapIntent.setPackage("com.google.android.apps.maps")
                            if (mapIntent.resolveActivity(context.packageManager) != null) {
                                context.startActivity(mapIntent)
                            }
                        }
                    )

                    // Store Hours Card
                    ContactCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.AccessTime,
                        title = "Store Hours",
                        content = "Mon-Fri: 10AM - 8PM"
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
//            AboutUsTwoImagesSection()
//            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ContactCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    content: String,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .then(
                if (onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun HeadSection(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.about),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Semi-transparent overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.about_us),
                style = MaterialTheme.typography.displayMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate(Screen.Category.route) },
                modifier = Modifier
                    .width(200.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    surfaceContainerLight,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = stringResource(R.string.shop_now),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}


@Composable
fun AboutSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Decorative header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(3.dp)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        RoundedCornerShape(1.5.dp)
                    )
            )
            Text(
                text = stringResource(R.string.learn),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.primary
            )
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(3.dp)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        RoundedCornerShape(1.5.dp)
                    )
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.about),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.about_text_3),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 24.sp
                )
            }
        }
    }
}

@Composable
fun ContactSection() {
    val context = LocalContext.current
    var location by remember { mutableStateOf<Location?>(null) }
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    var showPermissionDialog by remember { mutableStateOf(false) }

    // Initialize FusedLocationProviderClient
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    // Request location updates
    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission) {
            try {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { lastLocation ->
                        location = lastLocation
                    }
            } catch (e: SecurityException) {
                // Handle permission denied
                showPermissionDialog = true
            }
        } else {
            showPermissionDialog = true
        }
    }

    // Show permission dialog
    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = { Text("Location Permission Required") },
            text = { Text("Please grant location permission to see your distance from our store.") },
            confirmButton = {
                TextButton(onClick = {
                    showPermissionDialog = false
                    // Open app settings
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", context.packageName, null)
                    intent.data = uri
                    context.startActivity(intent)
                }) {
                    Text("Open Settings")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPermissionDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Section Title
        Text(
            text = "Contact Information",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Phone Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:+94771234567")
                    context.startActivity(intent)
                },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Phone",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Call Us",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "+94 77 123 4567",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        // Location Card with distance
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable {
                    val storeLocation = Location("store").apply {
                        latitude = 6.9271  // Store's latitude
                        longitude = 79.8612 // Store's longitude
                    }

                    // If we have user's location, use it to show directions
                    val uri = if (location != null) {
                        // Show directions from current location to store
                        Uri.parse("google.navigation:q=6.9271,79.8612")
                    } else {
                        // Just show store location
                        Uri.parse("geo:6.9271,79.8612?q=Euphoria+Colombo")
                    }

                    val mapIntent = Intent(Intent.ACTION_VIEW, uri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    if (mapIntent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(mapIntent)
                    }
                },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Visit Our Store",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "123 Galle Road, Colombo 03",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (location != null) {
                        val storeLocation = Location("store").apply {
                            latitude = 6.9271
                            longitude = 79.8612
                        }
                        val distance = location!!.distanceTo(storeLocation) / 1000 // Convert to kilometers
                        Text(
                            text = String.format("%.1f km away", distance),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        // Store Hours Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Store Hours",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Monday - Friday: 10:00 AM - 8:00 PM",
                    style = MaterialTheme.typography.bodyMedium
                )
//                Text(
//                    text = "Saturday - Sunday: 11:00 AM - 6:00 PM",
//                    style = MaterialTheme.typography.bodyMedium
//                )
            }
        }
    }
}


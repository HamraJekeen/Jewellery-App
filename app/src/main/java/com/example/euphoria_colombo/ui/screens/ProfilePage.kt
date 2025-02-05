package com.example.euphoria_colombo.ui.screens

import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.euphoria_colombo.R
import com.example.euphoria_colombo.Screen
import com.example.euphoria_colombo.ui.AuthState
import com.example.euphoria_colombo.ui.AuthViewModel

@Composable
fun UserProfileScreen(navController: NavHostController, authViewModel: AuthViewModel) {


    val userName = "John Doe"
    val userEmail = "johndoe@example.com"

    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate(Screen.Account.route)
            else -> Unit
        }
    }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    // Define button width for portrait and landscape modes
    val buttonWidthModifier = if (isLandscape) {
        Modifier.width(200.dp)
    } else {
        Modifier.fillMaxWidth().padding(horizontal = 32.dp)
    }

    val context = LocalContext.current

    // State to store the profile picture URI or Bitmap
    var profilePictureUri by remember { mutableStateOf<Uri?>(null) }
    var capturedImageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Camera launcher
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            capturedImageBitmap = bitmap
        }
    }

    // Gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        profilePictureUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.onPrimary),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        // Profile Picture
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.LightGray, shape = CircleShape)
                .padding(8.dp)
        ) {
            if (capturedImageBitmap != null) {
                Image(
                    bitmap = capturedImageBitmap!!.asImageBitmap(),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape) // Clip the image to a circle
                        .background(Color.LightGray) // Optional background color
                        .fillMaxSize(), // Ensures the image fills the circle area
                    contentScale = ContentScale.Crop
                )
            } else if (profilePictureUri != null) {
                Image(
                    painter = rememberImagePainter(profilePictureUri),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape) // Clip the image to a circle
                        .background(Color.LightGray) // Optional background color
                        .fillMaxSize(), // Ensures the image fills the circle area
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.profile_pic),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape) // Clip the image to a circle
                        .background(Color.LightGray) // Optional background color
                        .fillMaxSize(), // Ensures the image fills the circle area
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to launch the camera
        Button(onClick = { cameraLauncher.launch(null) }) {
            Text(text = "Take Picture")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to pick from the gallery
        Button(onClick = { galleryLauncher.launch("image/*") }) {
            Text(text = "Pick from Gallery")
        }

        Spacer(modifier = Modifier.height(16.dp))
        // Username
        Text(
            text = userName,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Email
        Text(
            text = userEmail,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Logout Button
        Button(
            onClick = { authViewModel.signout() },
            enabled = authState.value != AuthState.Loading,
            modifier = buttonWidthModifier
        ) {
            Text(text = "Logout")
        }
    }
}
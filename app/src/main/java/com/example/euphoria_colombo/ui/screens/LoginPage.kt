package com.example.euphoria_colombo.ui.screens

import android.content.res.Configuration
import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.euphoria_colombo.R
import com.example.euphoria_colombo.Screen
import com.example.euphoria_colombo.ui.AuthState
import com.example.euphoria_colombo.ui.AuthViewModel
import com.example.euphoria_colombo.ui.theme.onSurfaceVariantLight
import com.example.euphoria_colombo.ui.theme.secondaryLight

@Composable
fun LoginScreen(navController: NavHostController,authViewModel: AuthViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {

        Box(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                LoginScreenContent(navController,authViewModel)


            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenContent(navController: NavHostController, authViewModel: AuthViewModel) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }




    val configuration = LocalConfiguration.current

    val authState = authViewModel.authState.observeAsState()

    val context = LocalContext.current

    LaunchedEffect (authState.value){
        when(authState.value){
            is AuthState.Authenticated -> navController.navigate(Screen.Home.route)
            is AuthState.Error-> Toast.makeText(context,(
                    authState.value as AuthState.Error).message,Toast.LENGTH_SHORT).show()
            else-> Unit
        }

    }

    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp)

                //.background(MaterialTheme.colorScheme.onPrimary)
            ) {

//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                    ,
//                    horizontalArrangement = Arrangement.End
//                ) {
//                    IconButton(onClick = {
//                        navController.navigate(Screen.Home.route)
//                    }) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_close),
//                            contentDescription = "Close",
//                            tint = MaterialTheme.colorScheme.onSurface
//                        )
//                    }
//                }
                Spacer(modifier = Modifier.height(104.dp))

                Column(
                    modifier = Modifier
                        //.fillMaxSize()
                        .height(450.dp)
                        .width(550.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = MaterialTheme.shapes.medium,
                            clip = false
                        )
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Title
                    Text(
                        text = stringResource(R.string.login),
//                fontSize = 32.sp,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 24.dp, start = 20.dp, end = 20.dp)
                    )

                    // Username TextField
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(stringResource(R.string.email)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 26.dp)
                            .background(MaterialTheme.colorScheme.surface),
                        colors = textFieldColors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password TextField
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(stringResource(R.string.password)) },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = image, contentDescription = null)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 26.dp)
                            .background(MaterialTheme.colorScheme.surface),
                        colors = textFieldColors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Login Button
                    Button(
                        onClick = { authViewModel.login(email,password) },
                        enabled = authState.value != AuthState.Loading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 36.dp)
                            .height(48.dp)
                    ) {
                        Text(text = stringResource(R.string.login), fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Signup Navigation
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.regNotYet),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = stringResource(R.string.CreateAccount),
                            modifier = Modifier
                                .clickable { navController.navigate("register_screen")}
                                .padding(start = 4.dp),
                            color = MaterialTheme.colorScheme.primary,
//                    fontSize = 14.sp
                            style = MaterialTheme.typography.bodySmall
                        )

                    }
                }
            }
        }
        else -> {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp)

                //.background(MaterialTheme.colorScheme.onPrimary)
            ) {

//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                    ,
//                    horizontalArrangement = Arrangement.End
//                ) {
//                    IconButton(onClick = {
//                        navController.navigate(Screen.Home.route) // Navigate to the home screen
//                    }) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_close),
//                            contentDescription = "Close",
//                            tint = MaterialTheme.colorScheme.onSurface
//                        )
//                    }
//                }
                Spacer(modifier = Modifier.height(104.dp))

                Column(
                    modifier = Modifier
                        //.fillMaxSize()
                        .height(650.dp)
                        .padding(horizontal = 14.dp, vertical = 16.dp)

                        .shadow(
                            elevation = 8.dp,
                            shape = MaterialTheme.shapes.medium,
                            clip = false
                        )
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(14.dp),

                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Title
                    Text(
                        text = stringResource(R.string.login),

                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 24.dp, start = 20.dp, end = 20.dp)
                    )

                    // Username TextField
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(stringResource(R.string.email)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 26.dp)
                            .background(MaterialTheme.colorScheme.surface),
                        colors = textFieldColors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password TextField
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(stringResource(R.string.password)) },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = image, contentDescription = null)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 26.dp)
                            .background(MaterialTheme.colorScheme.surface),
                        colors = textFieldColors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Login Button
                    Button(
                        onClick = { authViewModel.login(email,password)} ,
                        enabled = authState.value != AuthState.Loading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 36.dp)
                            .height(48.dp)
                    ) {
                        Text(text = stringResource(R.string.login), fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Signup Navigation
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.regNotYet),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = stringResource(R.string.CreateAccount),
                            modifier = Modifier
                                .clickable { navController.navigate("register_screen")} // Navigate to register screen
                                .padding(start = 4.dp),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }


}



package com.example.euphoria_colombo.ui.screens
import android.app.DatePickerDialog
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import com.example.euphoria_colombo.R
import java.util.*
@Composable
fun CheckOutScreen(navController: NavHostController) {
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
                CheckoutPage(navController)



            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutPage(navController: NavHostController) {

    var name by rememberSaveable  { mutableStateOf("") }
    var Lastname by rememberSaveable  { mutableStateOf("") }
    var telephone by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }
    var city by rememberSaveable { mutableStateOf("") }
    var province by rememberSaveable { mutableStateOf("") }
    var postalCode by rememberSaveable { mutableStateOf("") }
    var selectedPaymentMethod by rememberSaveable { mutableStateOf("") }
    var termsAccepted by rememberSaveable { mutableStateOf(false) }
    var expanded by rememberSaveable { mutableStateOf(false) }


    val paymentMethods = listOf("Card Payment", "Cash On Delivery")


    val configuration = LocalConfiguration.current

    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 14.dp, bottom = 24.dp, start = 14.dp, end = 24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Back button
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.checkout),
                        fontSize = 32.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }


                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(stringResource(R.string.first_name)) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        colors = outlinedTextFieldColors(unfocusedBorderColor = Color.Gray)
                    )
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(stringResource(R.string.last_name)) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        colors = outlinedTextFieldColors(unfocusedBorderColor = Color.Gray)
                    )

                }


                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = telephone,
                        onValueChange = { telephone = it },
                        label = { Text(stringResource(R.string.phone_number))},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        colors = outlinedTextFieldColors(unfocusedBorderColor = Color.Gray)
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(stringResource(R.string.email)) },

                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        colors = outlinedTextFieldColors(unfocusedBorderColor = Color.Gray)
                    )

                }


                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text(stringResource(R.string.street)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedTextFieldColors(unfocusedBorderColor = Color.Gray)
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = city,
                        onValueChange = { city = it },
                        label = { Text(stringResource(R.string.city)) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        colors = outlinedTextFieldColors(unfocusedBorderColor = Color.Gray)
                    )
                    OutlinedTextField(
                        value = province,
                        onValueChange = { province = it },
                        label = { Text(stringResource(R.string.province)) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        colors = outlinedTextFieldColors(unfocusedBorderColor = Color.Gray)
                    )
                    OutlinedTextField(
                        value = postalCode,
                        onValueChange = { postalCode = it },
                        label = { Text(stringResource(R.string.postal_code))},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        colors = outlinedTextFieldColors(unfocusedBorderColor = Color.Gray)
                    )
                }

                // Payment Method Dropdown
                var dropdownWidth by remember { mutableStateOf(Size.Zero) } // Store width of the text field
                val icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Payment Method selection
                    OutlinedTextField(
                        value = selectedPaymentMethod,
                        onValueChange = {},
                        label = { Text(stringResource(R.string.payment_method)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                dropdownWidth = coordinates.size.toSize()
                            },
                        trailingIcon = {
                            Icon(
                                icon,
                                contentDescription = null,
                                modifier = Modifier.clickable { expanded = !expanded }
                            )
                        },
                        readOnly = true,
                        colors = outlinedTextFieldColors(unfocusedBorderColor = Color.Gray)
                    )

                    // Dropdown Menu for Payment Methods
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .width(with(LocalDensity.current) { dropdownWidth.width.toDp() })
                            .background(color = MaterialTheme.colorScheme.onPrimary)
                    ) {
                        paymentMethods.forEach { option ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedPaymentMethod = option
                                    expanded = false
                                },
                                text = { Text(option) }
                            )
                        }
                    }
                }

                // Terms and Conditions Checkbox
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = termsAccepted,
                        onCheckedChange = { termsAccepted = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(R.string.i_agree_to_the_terms_and_conditions))
                }

                // Checkout Button
                Button(
                    onClick = {
                        // Handle checkout logic here
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(stringResource(R.string.proceed_to_payment),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White)
                }
            }


        }
        else -> {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 0.dp, start = 0.dp, end = 24.dp),
            ) {

                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))


                Text(
                    text = stringResource(R.string.checkout),

                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 0.dp, start = 0.dp, end = 20.dp)
                )
            }



            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 14.dp, bottom = 24.dp, start = 14.dp, end = 24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {


                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.first_name)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedTextFieldColors(unfocusedBorderColor = Color.Gray)
                )
                OutlinedTextField(
                    value = Lastname,
                    onValueChange = { Lastname = it },
                    label = { Text(stringResource(R.string.last_name)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedTextFieldColors(unfocusedBorderColor = Color.Gray)
                )
                OutlinedTextField(
                    value = telephone,
                    onValueChange = { telephone = it },
                    label = { Text(stringResource(R.string.phone_number)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedTextFieldColors(

                        unfocusedBorderColor = Color.Gray
                    )
                )


                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text(stringResource(R.string.street)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedTextFieldColors(

                        unfocusedBorderColor = Color.Gray
                    )
                )
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text(stringResource(R.string.city)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedTextFieldColors(

                        unfocusedBorderColor = Color.Gray
                    )
                )
                OutlinedTextField(
                    value = province,
                    onValueChange = { province = it },
                    label = { Text(stringResource(R.string.province)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedTextFieldColors(

                        unfocusedBorderColor = Color.Gray
                    )
                )
                OutlinedTextField(
                    value = postalCode,
                    onValueChange = { postalCode = it },
                    label = { Text(stringResource(R.string.postal_code)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedTextFieldColors(

                        unfocusedBorderColor = Color.Gray
                    )
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(stringResource(R.string.email)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedTextFieldColors(

                        unfocusedBorderColor = Color.Gray
                    )
                )

                // Payment Method Dropdown
                var dropdownWidth by remember { mutableStateOf(Size.Zero) } // Store width of the text field
                val icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // TextField for payment method selection
                    OutlinedTextField(
                        value = selectedPaymentMethod,
                        onValueChange = {},
                        label = { Text(stringResource(R.string.payment_method)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                dropdownWidth = coordinates.size.toSize()
                            },
                        trailingIcon = {
                            Icon(
                                icon,
                                contentDescription = null,
                                modifier = Modifier.clickable { expanded = !expanded }
                            )
                        },
                        readOnly = true,
                        colors = outlinedTextFieldColors(

                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    // Dropdown menu for payment methods
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },


                        modifier = Modifier
                            .width(with(LocalDensity.current) { dropdownWidth.width.toDp() })
                            .background(color = MaterialTheme.colorScheme.onPrimary)

                    ) {
                        paymentMethods.forEach { option ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedPaymentMethod = option
                                    expanded = false
                                },
                                text = { Text(option) },


                                )
                        }
                    }
                }

                // Terms and Conditions Checkbox
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = termsAccepted,
                        onCheckedChange = { termsAccepted = it },
                        colors = CheckboxDefaults.colors(

                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(R.string.i_agree_to_the_terms_and_conditions))
                }

                // Checkout Button
                Button(
                    onClick = {
                        // Handle checkout logic here
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),

                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(stringResource(R.string.proceed_to_payment),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White)
                }
            }
        }


    }}

package com.example.euphoria_colombo.data

import android.content.Context
import com.example.euphoria_colombo.model.Product
import com.example.euphoria_colombo.model.Category
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import android.util.Log

class ProductDataSource(private val context: Context) {

    fun loadProductsFromJson(): List<Product> {
        val jsonString = context.assets.open("products.json").bufferedReader().use { it.readText() }
        val gson = Gson()
        val productResponse = gson.fromJson(jsonString, Map::class.java)
        val chains = productResponse["chains"] as? List<Map<String, Any>> ?: emptyList()

        return chains.map { productJson ->
            val id = productJson["id"] as? String ?: "unknown_id"
            val title = productJson["title"] as? String ?: "Unknown Title"
            val price = productJson["price"] as? String ?: "0.00"
            val image = productJson["image"] as? String ?: "default_image"

            // Log the image name being used
            Log.d("ProductDataSource", "Loading image: $image")

            val imageResId = context.resources.getIdentifier(image, "drawable", context.packageName)

            // Log the resource ID
            Log.d("ProductDataSource", "Image resource ID: $imageResId")

            Product(
                id = id,
                name = title,
                slug = id,
                image = listOf(imageResId.toString()), // Store the resource ID as a string
                description = "",
                price = price,
                is_active = 1,
                is_featured = 0,
                in_stock = 1,
                on_sale = 0,
                category = Category(1, "Chains", "chains", "", 1, "", ""),
                created_at = "",
                updated_at = ""
            )
        }
    }
    fun loadProductsFromJsonEarrings(): List<Product> {
        val jsonString = context.assets.open("products.json").bufferedReader().use { it.readText() }
        val gson = Gson()
        val productResponse = gson.fromJson(jsonString, Map::class.java)
        val earrings = productResponse["earrings"] as? List<Map<String, Any>> ?: emptyList()

        return earrings.map { productJson ->
            val id = productJson["id"] as? String ?: "unknown_id"
            val title = productJson["title"] as? String ?: "Unknown Title"
            val price = productJson["price"] as? String ?: "0.00"
            val image = productJson["image"] as? String ?: "default_image"

            // Log the image name being used
            Log.d("ProductDataSource", "Loading image: $image")

            val imageResId = context.resources.getIdentifier(image, "drawable", context.packageName)

            // Log the resource ID
            Log.d("ProductDataSource", "Image resource ID: $imageResId")

            Product(
                id = id,
                name = title,
                slug = id,
                image = listOf(imageResId.toString()), // Store the resource ID as a string
                description = "",
                price = price,
                is_active = 1,
                is_featured = 0,
                in_stock = 1,
                on_sale = 0,
                category = Category(1, "Earrings", "earrings", "", 1, "", ""),
                created_at = "",
                updated_at = ""
            )
        }
    }
    fun loadProductsFromJsonRings(): List<Product> {
        val jsonString = context.assets.open("products.json").bufferedReader().use { it.readText() }
        val gson = Gson()
        val productResponse = gson.fromJson(jsonString, Map::class.java)
        val rings = productResponse["rings"] as? List<Map<String, Any>> ?: emptyList()

        return rings.map { productJson ->
            val id = productJson["id"] as? String ?: "unknown_id"
            val title = productJson["title"] as? String ?: "Unknown Title"
            val price = productJson["price"] as? String ?: "0.00"
            val image = productJson["image"] as? String ?: "default_image"

            // Log the image name being used
            Log.d("ProductDataSource", "Loading image: $image")

            val imageResId = context.resources.getIdentifier(image, "drawable", context.packageName)

            // Log the resource ID
            Log.d("ProductDataSource", "Image resource ID: $imageResId")

            Product(
                id = id,
                name = title,
                slug = id,
                image = listOf(imageResId.toString()), // Store the resource ID as a string
                description = "",
                price = price,
                is_active = 1,
                is_featured = 0,
                in_stock = 1,
                on_sale = 0,
                category = Category(1, "Rings", "rings", "", 1, "", ""),
                created_at = "",
                updated_at = ""
            )
        }
    }
}
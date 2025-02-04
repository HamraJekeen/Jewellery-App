package com.example.euphoria_colombo.model

data class Product(
    val id: String,
    val name: String,
    val slug: String,
    val image: List<String>,
    val description: String,
    val price: String,
    val is_active: Int,
    val is_featured: Int,
    val in_stock: Int,
    val on_sale: Int,
    val category: Category,
    val created_at: String,
    val updated_at: String
)

data class Category(
    val id: Int,
    val name: String,
    val slug: String,
    val image: String,
    val is_active: Int,
    val created_at: String,
    val updated_at: String
)
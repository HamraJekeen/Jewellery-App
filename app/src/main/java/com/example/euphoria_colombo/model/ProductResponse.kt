package com.example.euphoria_colombo.model

data class ProductResponse(
    val data: List<Product>,

    val links: Links,
    val meta: Meta
)

data class Links(
    val first: String,
    val last: String,
    val prev: String?,
    val next: String?
)

data class Meta(
    val current_page: Int,
    val from: Int,
    val last_page: Int,
    val links: List<MetaLink>,
    val path: String,
    val per_page: Int,
    val to: Int,
    val total: Int
)

data class MetaLink(
    val url: String?,
    val label: String,
    val active: Boolean
)

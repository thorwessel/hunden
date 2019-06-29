package models

import java.math.BigDecimal

data class Product(
    val id: Int,
    val productName: String,
    val price: BigDecimal,
    val url: String
)
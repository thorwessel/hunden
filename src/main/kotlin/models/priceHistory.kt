package models

import java.math.BigDecimal

data class PriceHistory(
    val id: Int,
    val productId: Int,
    val price: BigDecimal,
    val date: String
)
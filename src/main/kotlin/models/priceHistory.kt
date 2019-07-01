package models

import org.joda.time.DateTime
import java.math.BigDecimal

data class PriceHistory(
    val id: Int,
    val productId: Int,
    val price: BigDecimal,
    val date: String
)
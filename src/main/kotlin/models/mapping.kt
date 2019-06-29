package models

import data.PriceHistories
import data.Products
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toProduct(): Product = Product(
    id = this[Products.id],
    productName = this[Products.productName],
    price = this[Products.price]
)

fun ResultRow.history(): PriceHistory = PriceHistory(
    id = this[PriceHistories.id],
    productId = this[PriceHistories.productId],
    price = this[PriceHistories.price],
    date = this[PriceHistories.date]
)
package data

import org.jetbrains.exposed.sql.Table

object Products : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val productName = text("product_name")
    val price = decimal("price", 1000, 10)
    val url = text("url")
}

object PriceHistories : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val productId = reference("product_id", Products.id)
    val price = decimal("price", 1000, 10)
    val date = text("date")
}
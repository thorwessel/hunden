package data

import models.Product
import models.toProduct
import models.PriceHistory
import models.history
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class HundenDB(private val db: Database) {
    fun fetch(id: Int): Product? {
        // transaction(db) runs the internal query as a new database transaction.
        return transaction(db) {
            // Returns the first invoice with matching id.
            Products
                .select { Products.id.eq(id) }
                .firstOrNull()
                ?.toProduct()
        }
    }

    fun fetchProducts(): List<Product> {
        return transaction(db) {
            Products
                .selectAll()
                .map { it.toProduct() }
        }
    }

    fun fetchHistory(): List<PriceHistory> {
        return transaction(db) {
            Products
                .selectAll()
                .map { it.history() }
        }
    }

    fun addProduct(product: Product): Product? {
        val id = transaction(db) {
            Products
                .insert {
                    it[this.price] = product.price
                    it[this.productName] = product.productName
                    it[this.url] = product.url
                } get Products.id
        }
        return fetch(id!!)
    }
}
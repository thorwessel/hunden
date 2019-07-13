package services

import data.HundenDB
import exceptions.ProductNotFound
import models.PriceHistory
import models.Product
import java.math.BigDecimal

class ProductService(private val dal: HundenDB) {
    fun fetchAll(): List<Product> {
        return dal.fetchProducts()
    }

    fun fetch(id: Int): Product {
        return dal.fetch(id) ?: throw ProductNotFound(id)
    }

    fun addProduct(price: BigDecimal, productName: String, url: String): Product? {
        return dal.addProduct(price, productName, url)
    }

    fun fetchHistory(id: Int): List<PriceHistory> {
        return dal.fetchHistory(id)
    }

    fun deleteProduct(id: Int): Product {
        return dal.deleteProduct(id) ?: throw ProductNotFound(id)
    }
}
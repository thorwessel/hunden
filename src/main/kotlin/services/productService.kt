package services

import data.HundenDB
import exceptions.ProductNotFound
import models.Product
import java.math.BigDecimal

class ProductService(private val dal: HundenDB) {
    fun fetchAll(): List<Product> {
        return dal.fetchProducts()
    }

    fun fetch(id: Int): Product {
        return dal.fetch(id) ?: throw ProductNotFound(id)
    }

    fun addProduct(price: BigDecimal, productName: String, url: String) {
        dal.addProduct(price, productName, url)
    }
}
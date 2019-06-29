package main

import data.HundenDB
import exceptions.ProductNotFound
import models.Product

class ProductService(private val dal: HundenDB) {
    //fun fetchAll(): List<Product> {
    //    return
    //}

    fun fetch(id: Int): Product {
        return dal.fetch(id) ?: throw ProductNotFound(id)
    }

    fun addProduct(product: Product) {
        dal.addProduct(product)
    }
}
package services

import data.HundenDB
import models.Product
import org.jsoup.Jsoup
import java.math.BigDecimal

class HistoryService(
    private val db: HundenDB
) {
    fun updateAllProducts() {
        val products = db.fetchProducts()
        products.forEach {
            updateHistory(it)
        }
    }

    private fun updateHistory(product: Product) {
        db.addHistory(product, fetchPrice(product.url))
    }

    fun fetchPrice(url: String): BigDecimal {
        val doc = Jsoup.connect(url).get().select("meta[itemprop=lowPrice]").toString()
        var lowPrice = ""
        for (x in doc) {
            if (x.isDigit()) lowPrice += x
        }
        return lowPrice.toBigDecimal()
    }
}
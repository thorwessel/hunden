package services

import data.HundenDB
import exceptions.ProductNotFound
import models.Product
import org.jsoup.Jsoup
import java.math.BigDecimal

class HistoryService(
    private val db: HundenDB
) {
    fun updateHistory(id: Int) {
        val product = db.fetch(id)
        product?.let {
            db.addHistory(product, fetchPrice(it))
        }
    }

    fun fetchPrice(product: Product): BigDecimal {
        val doc = Jsoup.connect(product.url).get().select("meta[itemprop=lowPrice]").toString()
        var lowPrice = ""
        for (x in doc) {
            if (x.isDigit()) lowPrice += x
        }
        return lowPrice.toBigDecimal()
    }
}
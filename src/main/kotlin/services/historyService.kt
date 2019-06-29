package services

import data.HundenDB
import org.jsoup.Jsoup
import java.math.BigDecimal

class HistoryService(
    private val db: HundenDB
) {
    fun updateHistory(id: Int) {
        val product = db.fetch(id)

    }

    fun fetchPrice(): BigDecimal {
        val doc = Jsoup.connect("https://www.pricerunner.dk/pl/40-4707682/CPU/Intel-Core-i7-9700K-3.6GHz-Box-Sammenlign-Priser").get().select("meta[itemprop=lowPrice]").toString()
        var lowPrice = ""
        for (x in doc) {
            if (x.isDigit()) lowPrice += x
        }
        return lowPrice.toBigDecimal()
    }
}
package rest

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import services.ProductService
import org.jetbrains.exposed.exceptions.EntityNotFoundException
import services.HistoryService

class HundenRest(
    private val productService: ProductService,
    private val historyService: HistoryService
) : Runnable {

    override fun run() {
        app.enableCorsForAllOrigins()
        app.start(7000)
    }

    // Set up Javalin rest app
    private val app = Javalin
        .create()
        .apply {
            // InvoiceNotFoundException: return 404 HTTP status code
            exception(EntityNotFoundException::class.java) { _, ctx ->
                ctx.status(404)
            }
            // Unexpected exception: return HTTP 500
            exception(Exception::class.java) { e, _ ->
               println("Internal server error")
            }
            // On 404: return message
            error(404) { ctx -> ctx.json("not found") }
        }

    init {
        // Set up URL endpoints for the rest app
        app.routes {
            path("rest") {
                // URL: /rest/health
                get("health") {
                    it.json("ok")
                }

                // V1
                path("v1") {
                    // URL: /rest/v1
                    path("products") {
                        // URL: /rest/v1/products
                        get {
                            it.json(productService.fetchAll())
                        }
                        // URL: /rest/v1/products/{:id}
                        get(":id") {
                            it.json(productService.fetch(it.pathParam("id").toInt()))
                        }
                        // URL: /rest/v1/products/add
                        post("add") {
                            //TODO handle incorrect JSON
                            val productName = it.formParam("productName")
                            val url = it.formParam("url")
                            if (url != null && productName != null) {
                                val price = historyService.fetchPrice(url)
                                val result = productService.addProduct(price = price, productName = productName.toString(), url = url.toString())
                                println("$productName was added with price: $price")
                                if (result != null) {
                                    historyService.updateHistory(result, price)
                                    it.status(200).json(result)
                                } else {
                                    it.status(404)
                                }
                            }
                        }
                    }
                    // URL: /rest/v1/history
                    path("history") {
                        // URL: /rest/v1/history/{id}
                        get(":id") {
                            it.json(productService.fetchHistory(it.pathParam("id").toInt()))
                        }
                    }

                    path("update") {
                        // URL: /rest/v1/update/secureendpoint
                        get("secureendpoint") {
                            historyService.updateAllProducts()
                        }
                    }
                }
            }
        }
    }
}
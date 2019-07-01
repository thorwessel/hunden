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
                            val price = it.formParam("price")
                            val productName = it.formParam("productName")
                            val url = it.formParam("url")
                            if (price != null && productName != null && url != null) {
                                productService.addProduct(price = price.toInt().toBigDecimal(), productName = productName.toString(), url = url.toString())
                            }
                        }
                    }
                    path("history") {

                        get(":id") {
                            it.json(productService.fetchHistory(it.pathParam("id").toInt()))
                        }
                    }

                }
            }
        }
    }
}
package main

import data.HundenDB
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection
import data.PriceHistories
import data.Products
import rest.HundenRest
import services.HistoryService
import services.ProductService

fun main() {

    val tables = arrayOf(Products, PriceHistories)

    val db = Database
            //Make sure DB is located in the below path!
            // /kode projekter/hunden/src/data/data.db
            //src/data/data.db
        .connect("jdbc:sqlite:/kode projekter/hunden/src/data/data.db", "org.sqlite.JDBC")
        .also {
            TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
            transaction(it) {
                addLogger(StdOutSqlLogger)
                SchemaUtils.drop(*tables)
                SchemaUtils.create(*tables)
            }
        }

    val dal = HundenDB(db = db)
    val productService = ProductService(dal = dal)

    val historyService = HistoryService(dal)


    HundenRest(
        productService = productService,
        historyService = historyService).run()
}
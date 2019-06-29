package main



import data.HundenDB
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.*
import java.sql.Connection
import data.PriceHistories
import data.Products
import rest.HundenRest

fun main() {

    val tables = arrayOf(Products, PriceHistories)

    val db = Database
        .connect("jdbc:sqlite:file:test?mode=memory&cache=shared", "org.sqlite.JDBC")
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

    HundenRest(productService = productService).run()
}
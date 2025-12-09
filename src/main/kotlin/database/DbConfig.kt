package dev.kokorev.database

import dev.kokorev.database.table.Messages
import dev.kokorev.database.tables.Users
import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun configureDatabase(application: Application): Database {
    val config = application.environment.config

    val url = config.property("datasource.url").getString()
    val driver = config.property("datasource.driver").getString()
    val user = config.property("datasource.user").getString()
    val password = config.property("datasource.password").getString()

    val db = Database.connect(
        url = url,
        driver = driver,
        user = user,
        password = password,
    )

    transaction(db) {
        SchemaUtils.create(Users)
        SchemaUtils.create(Messages)
    }

    return db
}
package dev.kokorev.plugin

import dev.kokorev.database.configureDatabase
import io.ktor.server.application.Application

fun Application.configureDb() {
    configureDatabase(this)
}
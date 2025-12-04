package dev.kokorev.plugin

import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        jackson()
    }

}
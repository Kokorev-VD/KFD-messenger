package dev.kokorev.controller.router

import dev.kokorev.controller.handler.MessageHandler
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing
import io.ktor.server.websocket.webSocket
import org.koin.ktor.ext.inject

fun Routing.messageRouter(): Route {
    val messageHandler by inject<MessageHandler>()
    return authenticate("jwt-auth") {
        webSocket("/messages") {
            messageHandler.consume(this)
        }
    }
}
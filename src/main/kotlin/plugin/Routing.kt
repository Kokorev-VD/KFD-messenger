package dev.kokorev.plugin

import dev.kokorev.controller.router.authRouter
import dev.kokorev.controller.router.helloRouter
import dev.kokorev.controller.router.messageRouter
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        helloRouter()
        messageRouter()
        authRouter()
    }
}

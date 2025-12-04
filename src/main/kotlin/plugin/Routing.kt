package dev.kokorev.plugin

import dev.kokorev.handler.impl.AuthHandlerImpl
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    val authHandler = AuthHandlerImpl()
    routing {
        authenticate("jwt-auth") {
            get("/hello") {
                call.respondText("Hello world!")
            }
        }
        route("/auth") {
            post("/registration") {
                authHandler.registration(call)
            }
            post("/login") {
                authHandler.login(call)
            }
        }
    }
}

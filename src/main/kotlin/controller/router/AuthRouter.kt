package dev.kokorev.controller.router

import dev.kokorev.controller.handler.AuthHandler
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Routing.authRouter(): Route {
    val authHandler: AuthHandler by inject<AuthHandler>()
    return route("/auth") {
        post("/registration") {
            authHandler.registration(call)
        }
        post("/login") {
            authHandler.login(call)
        }
    }
}
package dev.kokorev.controller.router

import dev.kokorev.model.common.UserPrincipal
import dev.kokorev.model.exception.UnauthorizedException
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.authentication
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get

fun Routing.helloRouter() =
    authenticate("jwt-auth") {
        get("/hello") {
            val principal = call.authentication.principal<UserPrincipal>() ?: throw UnauthorizedException()
            call.respondText("Hello ${principal.id}!")
        }
    }
package dev.kokorev.controller.handler.impl

import dev.kokorev.controller.handler.AuthHandler
import dev.kokorev.model.rq.AuthRequest
import dev.kokorev.service.AuthService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.annotation.Single

@Single
class AuthHandlerImpl(
    private val authService: AuthService,
): AuthHandler {
    override suspend fun login(call: RoutingCall){
        val rq = call.receive<AuthRequest>()
        call.respond(authService.login(rq))
    }

    override suspend fun registration(call: RoutingCall) {
        val rq = call.receive<AuthRequest>()
        call.respond(authService.registration(rq))
    }
}
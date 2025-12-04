package dev.kokorev.handler.impl

import dev.kokorev.handler.AuthHandler
import dev.kokorev.model.rq.AuthRequest
import dev.kokorev.service.AuthService
import dev.kokorev.service.impl.AuthServiceImpl
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class AuthHandlerImpl : AuthHandler {
    private val authService: AuthService = AuthServiceImpl()

    override suspend fun login(call: RoutingCall){
        val rq = call.receive<AuthRequest>()
        call.respond(authService.login(rq))
    }

    override suspend fun registration(call: RoutingCall) {
        val rq = call.receive<AuthRequest>()
        call.respond(authService.registration(rq))
    }
}
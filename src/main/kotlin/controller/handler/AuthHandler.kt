package dev.kokorev.controller.handler

import io.ktor.server.routing.RoutingCall

interface AuthHandler {
    suspend fun login(call: RoutingCall)

    suspend fun registration(call: RoutingCall)
}
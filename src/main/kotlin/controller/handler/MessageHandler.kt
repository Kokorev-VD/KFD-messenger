package dev.kokorev.controller.handler

import io.ktor.server.websocket.WebSocketServerSession

interface MessageHandler {
    suspend fun consume(session: WebSocketServerSession)
}
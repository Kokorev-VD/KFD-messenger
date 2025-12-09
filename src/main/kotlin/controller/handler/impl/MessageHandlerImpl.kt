package dev.kokorev.controller.handler.impl

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dev.kokorev.controller.handler.MessageHandler
import dev.kokorev.model.common.UserPrincipal
import dev.kokorev.model.domain.Message
import dev.kokorev.model.exception.UnauthorizedException
import dev.kokorev.model.rq.MessageRequest
import dev.kokorev.service.MessageService
import io.ktor.server.auth.authentication
import io.ktor.server.websocket.WebSocketServerSession
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single
import java.util.concurrent.ConcurrentHashMap

@Single
class MessageHandlerImpl(
    private val messageService: MessageService,
) : MessageHandler {
    private val sessions = ConcurrentHashMap<Int, WebSocketServerSession>()
    private val responseFlow = MutableSharedFlow<Message>()
    private val sharedFlow = responseFlow.asSharedFlow()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val objectMapper = jacksonObjectMapper()

    init {
        coroutineScope.launch {
            sharedFlow.collect { response ->
                val rawMessage = objectMapper.writeValueAsString(response)
                sessions[response.recipientId]?.send(Frame.Text(rawMessage))
            }
        }
    }

    override suspend fun consume(session: WebSocketServerSession) {
        val principal = session.call.authentication.principal<UserPrincipal>() ?: throw UnauthorizedException()
        sessions[principal.id] = session

        messageService.findByRecipientId(principal.id).forEach {
            responseFlow.emit(
                it
            )
        }

        session.incoming.consumeEach { frame ->
            if(frame !is Frame.Text) {
                return@consumeEach
            }
            val messageRq = objectMapper.readValue(frame.readText(), MessageRequest::class.java)
            val message = messageService.save(messageRq.text, authorId = principal.id, recipientId = messageRq.recipientId)
            responseFlow.emit(message)
        }

        sessions.remove(principal.id)
    }
}
package dev.kokorev.plugin

import dev.kokorev.model.exception.ApiException
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<ApiException> { call, exception ->
            val errorDto = exception.errorDto
            call.respond(errorDto.httpCode, errorDto)
        }
    }
}
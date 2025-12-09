package dev.kokorev.plugin

import dev.kokorev.model.exception.UnauthorizedException
import dev.kokorev.service.AuthService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.koin.ktor.ext.inject

fun Application.configureAuth() {
    val authService: AuthService by inject<AuthService>()
    install(Authentication) {
        jwt("jwt-auth") {
            verifier { authService.verifier() }
            validate { credential -> authService.validate(credential) }
            challenge { defaultSchema, realm -> throw UnauthorizedException() }
        }
    }
}
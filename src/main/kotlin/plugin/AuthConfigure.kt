package dev.kokorev.plugin

import dev.kokorev.model.exception.UnauthorizedException
import dev.kokorev.service.AuthService
import dev.kokorev.service.impl.AuthServiceImpl
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.jwt

fun Application.configureAuth() {
    val authService: AuthService = AuthServiceImpl()
    install(Authentication) {
        jwt("jwt-auth") {
            verifier { authService.verifier() }
            validate { credential -> authService.validate(credential) }
            challenge { defaultSchema, realm -> throw UnauthorizedException() }
        }
    }
}
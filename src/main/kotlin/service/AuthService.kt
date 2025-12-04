package dev.kokorev.service

import com.auth0.jwt.JWTVerifier
import dev.kokorev.model.rq.AuthRequest
import dev.kokorev.model.rs.TokenResponse
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal

interface AuthService {
    suspend fun login(rq: AuthRequest): TokenResponse

    suspend fun registration(rq: AuthRequest): TokenResponse

    fun verifier(): JWTVerifier

    fun validate(credential: JWTCredential): JWTPrincipal
}
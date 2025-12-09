package dev.kokorev.service.impl

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import dev.kokorev.model.common.UserPrincipal
import dev.kokorev.model.domain.CreateUser
import dev.kokorev.model.exception.UnauthorizedException
import dev.kokorev.model.rq.AuthRequest
import dev.kokorev.model.rs.TokenResponse
import dev.kokorev.service.AuthService
import dev.kokorev.service.UserService
import io.ktor.server.auth.jwt.*
import org.koin.core.annotation.Single
import org.mindrot.jbcrypt.BCrypt
import java.lang.System.currentTimeMillis
import java.util.*

@Single
class AuthServiceImpl(
    private val userService: UserService,
) : AuthService {
    private val secret = "very-secret-secret" // TODO вынести в application.yaml
    private val algorithm = Algorithm.HMAC256(secret)

    override suspend fun login(rq: AuthRequest): TokenResponse {
        val user = userService.get(rq.login) ?: throw UnauthorizedException()
        if (verifyPassword(rq.password, user.salt, user.hash)) {
            return TokenResponse(
                generateToken(user.id)
            )
        }
        throw UnauthorizedException()
    }

    override suspend fun registration(rq: AuthRequest): TokenResponse {
        val (hash, salt) = hashPassword(rq.password)
        val userId = userService.create(
            CreateUser(
                rq.login,
                hash,
                salt,
            )
        )
        return TokenResponse(generateToken(userId))
    }

    override fun verifier(): JWTVerifier = JWT.require(algorithm).build()

    override fun validate(credential: JWTCredential): UserPrincipal =
        UserPrincipal(credential.payload.claims["id"]?.asInt() ?: throw UnauthorizedException())

    private fun hashPassword(password: String): Pair<String, String> {
        val salt = BCrypt.gensalt()
        return BCrypt.hashpw(password, salt) to salt
    }

    private fun verifyPassword(password: String, salt: String, hash: String): Boolean {
        val hashed = BCrypt.hashpw(password, salt)
        return hashed == hash
    }

    private fun generateToken(id: Int): String {
        val expiresAt = Date(currentTimeMillis() + 1000 * 3600)

        return JWT.create()
            .withClaim("id", id)
            .withExpiresAt(expiresAt)
            .sign(algorithm)

    }

}
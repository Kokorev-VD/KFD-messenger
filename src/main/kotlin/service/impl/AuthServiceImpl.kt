package dev.kokorev.service.impl

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import dev.kokorev.model.domain.CreateUser
import dev.kokorev.model.exception.UnauthorizedException
import dev.kokorev.model.rq.AuthRequest
import dev.kokorev.model.rs.TokenResponse
import dev.kokorev.service.AuthService
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal
import org.mindrot.jbcrypt.BCrypt
import java.lang.System.currentTimeMillis
import java.util.Date

class AuthServiceImpl: AuthService {
    private val secret = "very-secret-secret"
    private val algorithm = Algorithm.HMAC256(secret)
    private val userService = UserServiceImpl()

    override suspend fun login(rq: AuthRequest): TokenResponse {
        val user = userService.get(rq.login) ?: throw UnauthorizedException()
        if(verifyPassword(rq.password, user.salt, user.hash)) {
            return TokenResponse(
                generateToken(user.login)
            )
        }
        throw UnauthorizedException()
    }

    override suspend fun registration(rq: AuthRequest): TokenResponse {
        val (hash, salt) = hashPassword(rq.password)
        val user = userService.create(
            CreateUser(
                rq.login,
                hash,
                salt,
            )
        )
        return TokenResponse(generateToken(user.login))
    }

    override fun verifier(): JWTVerifier = JWT.require(algorithm).build()

    override fun validate(credential: JWTCredential): JWTPrincipal = JWTPrincipal(credential.payload)

    private fun hashPassword(password: String): Pair<String, String> {
        val salt = BCrypt.gensalt()
        return BCrypt.hashpw(password, salt) to salt
    }

    private fun verifyPassword(password: String, salt: String, hash: String): Boolean {
        val hashed = BCrypt.hashpw(password, salt)
        return hashed == hash
    }

    private fun generateToken(login: String): String {
        val expiresAt = Date(currentTimeMillis() + 1000*3600)


        return JWT.create()
            .withClaim("login", login)
            .withExpiresAt(expiresAt)
            .sign(algorithm)

    }

}
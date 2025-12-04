package dev.kokorev.service

import dev.kokorev.model.domain.CreateUser
import dev.kokorev.model.domain.User

interface UserService {
    suspend fun create(user: CreateUser): User
    suspend fun get(login: String): User?
}
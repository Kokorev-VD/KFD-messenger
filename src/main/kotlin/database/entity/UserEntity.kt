package dev.kokorev.database.entity

import dev.kokorev.database.tables.Users
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object: IntEntityClass<UserEntity>(Users)

    var login by Users.login
    var hash by Users.hash
    var salt by Users.salt
}
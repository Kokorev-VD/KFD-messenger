package dev.kokorev

import dev.kokorev.plugin.configureAuth
import dev.kokorev.plugin.configureDb
import dev.kokorev.plugin.configureRouting
import dev.kokorev.plugin.configureSerialization
import dev.kokorev.plugin.configureStatusPages
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureAuth()
    configureRouting()
    configureDb()
    configureStatusPages()
    configureSerialization()
}

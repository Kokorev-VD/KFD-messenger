package dev.kokorev.plugin

import io.ktor.server.application.*
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module
import org.koin.ktor.plugin.Koin

@Module
@ComponentScan("dev.kokorev")
class AppModule

fun Application.configureKoin() {
    install(Koin){
        modules(
            AppModule().module
        )
    }
}
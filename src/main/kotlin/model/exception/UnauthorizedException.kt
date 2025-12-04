package dev.kokorev.model.exception

import io.ktor.http.HttpStatusCode

class UnauthorizedException: ApiException(
    ErrorDto(
        "Пользователь неавторизован",
        HttpStatusCode.Unauthorized,
    )
)
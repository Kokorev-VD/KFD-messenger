package dev.kokorev.model.exception

import io.ktor.http.HttpStatusCode

class ResourceNotFoundException(resource: String): ApiException(
    ErrorDto(
        "Ресурс $resource не найдена",
        HttpStatusCode.NotFound,
    )
)
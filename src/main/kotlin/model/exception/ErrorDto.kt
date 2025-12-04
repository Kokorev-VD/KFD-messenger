package dev.kokorev.model.exception

import com.fasterxml.jackson.annotation.JsonIgnore
import io.ktor.http.HttpStatusCode

data class ErrorDto(
    val message: String,
    @JsonIgnore
    val httpCode: HttpStatusCode,
)
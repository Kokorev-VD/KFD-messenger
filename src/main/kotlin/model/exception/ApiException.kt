package dev.kokorev.model.exception

open class ApiException(
    val errorDto: ErrorDto
) : RuntimeException(errorDto.message)
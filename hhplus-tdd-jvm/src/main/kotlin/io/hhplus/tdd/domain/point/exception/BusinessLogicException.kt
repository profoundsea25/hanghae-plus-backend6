package io.hhplus.tdd.domain.point.exception

class BusinessLogicException(
    val errorMessage: ErrorMessage,
    override val message: String = errorMessage.message,
    override val cause: Throwable? = null,
) : RuntimeException(message, cause)

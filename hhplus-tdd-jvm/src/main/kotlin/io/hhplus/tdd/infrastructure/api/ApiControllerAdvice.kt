package io.hhplus.tdd.infrastructure.api

import io.hhplus.tdd.domain.point.exception.BusinessLogicException
import io.hhplus.tdd.domain.point.exception.ErrorMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ApiControllerAdvice : ResponseEntityExceptionHandler() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorMessage> {
        return ResponseEntity(
            ErrorMessage.INTERNAL_SERVER_ERROR,
            HttpStatus.INTERNAL_SERVER_ERROR,
        )
    }

    @ExceptionHandler(BusinessLogicException::class)
    fun handleBusinessLogicException(e: BusinessLogicException): ResponseEntity<ErrorMessage> {
        return ResponseEntity(
            e.errorMessage,
            HttpStatus.BAD_REQUEST,
        )
    }
}

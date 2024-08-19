package y.june.prestudy.common.handler

import org.springframework.beans.ConversionNotSupportedException
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.converter.HttpMessageNotWritableException
import org.springframework.validation.BindException
import org.springframework.validation.method.MethodValidationException
import org.springframework.web.ErrorResponseException
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingPathVariableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.async.AsyncRequestTimeoutException
import org.springframework.web.method.annotation.HandlerMethodValidationException
import org.springframework.web.multipart.MaxUploadSizeExceededException
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.resource.NoResourceFoundException
import y.june.prestudy.common.api.*
import y.june.prestudy.common.exception.ApiException
import y.june.prestudy.common.exception.BadRequestException
import y.june.prestudy.common.exception.InternalServerException
import y.june.prestudy.common.logger

@RestControllerAdvice
class RestApiExceptionHandler {

    private val log = logger()

    @ExceptionHandler(Throwable::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGlobalException(e: Throwable): Response<Unit> {
        log.error("Unexpected Error Occurred", e)
        return internalServerErrorResponse()
    }

    private fun internalServerErrorResponse(): Response<Unit> {
        return Response(
            responseCode = ResponseCode.INTERNAL_SERVER_ERROR,
            body = Unit
        )
    }

    @ExceptionHandler(
        // See `ResponseEntityExceptionHandler#handleException`
        HttpRequestMethodNotSupportedException::class,
        HttpMediaTypeNotSupportedException::class,
        HttpMediaTypeNotAcceptableException::class,
        MissingPathVariableException::class,
        MissingServletRequestParameterException::class,
        MissingServletRequestPartException::class,
        ServletRequestBindingException::class,
        MethodArgumentNotValidException::class,
        HandlerMethodValidationException::class,
        NoHandlerFoundException::class,
        NoResourceFoundException::class,
        AsyncRequestTimeoutException::class,
        ErrorResponseException::class,
        MaxUploadSizeExceededException::class,
        ConversionNotSupportedException::class,
        TypeMismatchException::class,
        HttpMessageNotReadableException::class,
        HttpMessageNotWritableException::class,
        MethodValidationException::class,
        BindException::class,
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleSpringWebExceptions(e: Throwable): Response<Unit> {
        log.warn("Spring Web Exception Occurred", e)
        return springWebExceptionsResponse(e)
    }

    private fun springWebExceptionsResponse(e: Throwable): Response<Unit> {
        return Response(
            statusCode = ResponseCode.BAD_REQUEST.code,
            message = e.message.orEmpty(),
            body = Unit
        )
    }

    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestException(e: BadRequestException): Response<Unit> {
        log.warn("BadRequestException Occurred", e)
        return apiExceptionResponse(e)
    }

    @ExceptionHandler(InternalServerException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleInternalServerException(e: InternalServerException): Response<Unit> {
        log.warn("InternalServerException Occurred", e)
        return apiExceptionResponse(e)
    }

    private fun apiExceptionResponse(e: ApiException): Response<Unit> {
        return Response(
            statusCode = e.status.code,
            message = e.status.message,
            body = Unit
        )
    }

}

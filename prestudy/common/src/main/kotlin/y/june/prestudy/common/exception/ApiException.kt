package y.june.prestudy.common.exception

import y.june.prestudy.common.api.ResponseCode

sealed class ApiException(
    val status: ResponseCode,
    override val message: String,
    override val cause: Throwable?,
) : RuntimeException()


class BadRequestException(
    status: ResponseCode = ResponseCode.BAD_REQUEST,
    message: String = status.message,
    cause: Throwable? = null,
) : ApiException(
    status = status,
    message = message,
    cause = cause,
)

class InternalServerException(
    status: ResponseCode = ResponseCode.INTERNAL_SERVER_ERROR,
    message: String = status.message,
    cause: Throwable? = null,
) : ApiException(
    status = status,
    message = message,
    cause = cause,
)

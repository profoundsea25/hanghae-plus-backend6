package y.june.prestudy.common.api

data class Response<T>(
    val statusCode: String,
    val message: String,
    val body: T? = null,
) {
    constructor(responseCode: ResponseCode, body: T? = null) : this(
        statusCode = responseCode.code,
        message = responseCode.message,
        body = body
    )
}

fun <T> ok(body: T): Response<T> {
    return Response(
        responseCode = ResponseCode.OK,
        body = body
    )
}

fun ok(): Response<Unit> {
    return ok(Unit)
}

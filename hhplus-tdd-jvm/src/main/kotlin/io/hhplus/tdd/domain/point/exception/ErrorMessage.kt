package io.hhplus.tdd.domain.point.exception

enum class ErrorMessage(
    val status: Int,
    val message: String,
) {
    INTERNAL_SERVER_ERROR(500, "에러가 발생했습니다."),
    OVER_CHARGING_POINT(400, "최대 보유 가능 포인트는 5,000,000 포인트입니다."),
    OVER_USING_POINT(400, "포인트가 부족합니다."),
}

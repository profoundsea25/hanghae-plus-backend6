package io.hhplus.tdd.domain.point

data class UserPoint(
    val id: Long,
    val point: Long,
    val updateMillis: Long,
) {
    companion object {
        const val MAX_POINT_LIMIT: Long = 5_000_000L
        const val MIN_POINT_LIMIT: Long = 0L
    }
}

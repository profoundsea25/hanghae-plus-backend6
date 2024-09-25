package io.hhplus.tdd.domain.point

import io.hhplus.tdd.domain.point.exception.BusinessLogicException
import io.hhplus.tdd.domain.point.exception.ErrorMessage

data class UserPoint(
    val id: Long,
    val point: Long,
    val updateMillis: Long,
) {
    init {
        validatePoint()
    }

    companion object {
        const val MAX_POINT_LIMIT: Long = 5_000_000L
        const val MIN_POINT_LIMIT: Long = 0L
    }

    private fun validatePoint() {
        if (this.point > MAX_POINT_LIMIT) {
            throw BusinessLogicException(ErrorMessage.OVER_CHARGING_POINT)
        }
        if (this.point < MIN_POINT_LIMIT) {
            throw BusinessLogicException(ErrorMessage.OVER_USING_POINT)
        }
    }

    fun doTransaction(
        transactionType: TransactionType,
        amount: Long,
    ): UserPoint {
        val amountToOperate =
            when (transactionType) {
                TransactionType.CHARGE -> amount
                TransactionType.USE -> -amount
            }
        return UserPoint(
            id = this.id,
            point = this.point + amountToOperate,
            updateMillis = this.updateMillis,
        )
    }
}

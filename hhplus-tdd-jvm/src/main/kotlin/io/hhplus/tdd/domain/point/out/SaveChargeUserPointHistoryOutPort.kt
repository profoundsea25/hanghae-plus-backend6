package io.hhplus.tdd.domain.point.out

import io.hhplus.tdd.domain.point.PointHistory

interface SaveChargeUserPointHistoryOutPort {
    fun saveChargePointHistory(
        id: Long,
        amount: Long,
        updateMillis: Long,
    ): PointHistory
}

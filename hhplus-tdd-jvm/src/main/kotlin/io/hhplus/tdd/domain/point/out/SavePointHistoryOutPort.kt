package io.hhplus.tdd.domain.point.out

import io.hhplus.tdd.domain.point.PointHistory
import io.hhplus.tdd.domain.point.TransactionType

interface SavePointHistoryOutPort {
    fun savePointHistory(
        id: Long,
        amount: Long,
        transactionType: TransactionType,
        updateMillis: Long,
    ): PointHistory
}

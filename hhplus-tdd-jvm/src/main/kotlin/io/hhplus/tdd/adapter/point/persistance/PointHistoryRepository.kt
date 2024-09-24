package io.hhplus.tdd.adapter.point.persistance

import io.hhplus.tdd.domain.point.PointHistory
import io.hhplus.tdd.domain.point.TransactionType
import io.hhplus.tdd.domain.point.out.SaveChargeUserPointHistoryOutPort
import io.hhplus.tdd.infrastructure.database.PointHistoryTable
import org.springframework.stereotype.Repository

@Repository
class PointHistoryRepository(
    private val pointHistoryTable: PointHistoryTable,
) : SaveChargeUserPointHistoryOutPort {
    override fun saveChargePointHistory(
        id: Long,
        amount: Long,
        updateMillis: Long,
    ): PointHistory {
        return pointHistoryTable.insert(
            id = id,
            amount = amount,
            transactionType = TransactionType.CHARGE,
            updateMillis = updateMillis,
        )
    }
}

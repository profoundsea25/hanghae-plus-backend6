package io.hhplus.tdd.adapter.point.persistance

import io.hhplus.tdd.domain.point.PointHistory
import io.hhplus.tdd.domain.point.TransactionType
import io.hhplus.tdd.domain.point.out.FindPointHistoryOutPort
import io.hhplus.tdd.domain.point.out.SavePointHistoryOutPort
import io.hhplus.tdd.infrastructure.database.PointHistoryTable
import org.springframework.stereotype.Repository

@Repository
class PointHistoryRepository(
    private val pointHistoryTable: PointHistoryTable,
) : SavePointHistoryOutPort,
    FindPointHistoryOutPort {
    override fun savePointHistory(
        id: Long,
        amount: Long,
        transactionType: TransactionType,
        updateMillis: Long,
    ): PointHistory {
        return pointHistoryTable.insert(
            id = id,
            amount = amount,
            transactionType = transactionType,
            updateMillis = updateMillis,
        )
    }

    override fun findAllBy(userId: Long): List<PointHistory> {
        return pointHistoryTable.selectAllByUserId(userId)
    }
}

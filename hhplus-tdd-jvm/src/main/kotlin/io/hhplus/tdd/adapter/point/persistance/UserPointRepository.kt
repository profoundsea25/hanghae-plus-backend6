package io.hhplus.tdd.adapter.point.persistance

import io.hhplus.tdd.domain.point.UserPoint
import io.hhplus.tdd.domain.point.out.ChargeUserPointOutPort
import io.hhplus.tdd.domain.point.out.FindUserPointOutPort
import io.hhplus.tdd.infrastructure.database.UserPointTable
import org.springframework.stereotype.Repository

@Repository
class UserPointRepository(
    private val userPointTable: UserPointTable,
) : FindUserPointOutPort,
    ChargeUserPointOutPort {
    override fun findBy(userId: Long): UserPoint {
        return userPointTable.selectById(userId)
    }

    override fun charge(
        id: Long,
        amount: Long,
    ): UserPoint {
        return userPointTable.insertOrUpdate(id = id, amount = amount)
    }
}

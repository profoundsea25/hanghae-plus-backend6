package io.hhplus.tdd.adapter.point.persistance

import io.hhplus.tdd.domain.point.UserPoint
import io.hhplus.tdd.domain.point.out.FindUserPointOutPort
import io.hhplus.tdd.infrastructure.database.UserPointTable
import org.springframework.stereotype.Repository

@Repository
class UserPointRepository(
    private val userPointTable: UserPointTable,
) : FindUserPointOutPort {
    override fun findBy(id: Long): UserPoint {
        return userPointTable.selectById(id)
    }
}

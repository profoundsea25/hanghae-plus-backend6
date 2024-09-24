package io.hhplus.tdd.domain.point.out

import io.hhplus.tdd.domain.point.UserPoint

interface SaveUserPointOutPort {
    fun save(
        id: Long,
        amount: Long,
    ): UserPoint
}

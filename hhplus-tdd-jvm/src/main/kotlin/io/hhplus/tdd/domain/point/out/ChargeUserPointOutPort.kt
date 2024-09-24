package io.hhplus.tdd.domain.point.out

import io.hhplus.tdd.domain.point.UserPoint

interface ChargeUserPointOutPort {
    fun charge(
        id: Long,
        amount: Long,
    ): UserPoint
}

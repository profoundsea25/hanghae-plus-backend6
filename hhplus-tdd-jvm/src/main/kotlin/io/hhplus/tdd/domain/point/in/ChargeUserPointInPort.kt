package io.hhplus.tdd.domain.point.`in`

import io.hhplus.tdd.domain.point.UserPoint

interface ChargeUserPointInPort {
    fun charge(
        id: Long,
        amount: Long,
    ): UserPoint
}

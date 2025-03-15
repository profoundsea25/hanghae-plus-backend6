package io.hhplus.tdd.domain.point.`in`

import io.hhplus.tdd.domain.point.UserPoint

interface UseUserPointInPort {
    fun use(
        id: Long,
        amount: Long,
    ): UserPoint
}

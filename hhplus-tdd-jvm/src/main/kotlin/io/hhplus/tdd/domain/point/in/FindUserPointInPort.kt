package io.hhplus.tdd.domain.point.`in`

import io.hhplus.tdd.domain.point.UserPoint

interface FindUserPointInPort {
    fun findUserPointBy(userId: Long): UserPoint
}

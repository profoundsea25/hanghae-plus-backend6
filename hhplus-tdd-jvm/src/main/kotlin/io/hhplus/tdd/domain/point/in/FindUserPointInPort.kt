package io.hhplus.tdd.domain.point.`in`

import io.hhplus.tdd.domain.point.UserPoint

interface FindUserPointInPort {
    fun findBy(id: Long): UserPoint
}

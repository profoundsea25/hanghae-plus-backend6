package io.hhplus.tdd.domain.point.out

import io.hhplus.tdd.domain.point.UserPoint

interface FindUserPointOutPort {
    fun findBy(id: Long): UserPoint
}

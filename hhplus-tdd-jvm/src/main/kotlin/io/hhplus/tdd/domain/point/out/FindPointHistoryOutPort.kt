package io.hhplus.tdd.domain.point.out

import io.hhplus.tdd.domain.point.PointHistory

interface FindPointHistoryOutPort {
    fun findAllBy(userId: Long): List<PointHistory>
}

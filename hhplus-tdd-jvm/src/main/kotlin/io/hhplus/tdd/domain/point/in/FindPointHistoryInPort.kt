package io.hhplus.tdd.domain.point.`in`

import io.hhplus.tdd.domain.point.PointHistory

interface FindPointHistoryInPort {
    fun findAllBy(userId: Long): List<PointHistory>
}

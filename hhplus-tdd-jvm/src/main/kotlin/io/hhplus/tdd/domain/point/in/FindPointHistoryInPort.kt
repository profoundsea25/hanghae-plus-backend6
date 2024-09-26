package io.hhplus.tdd.domain.point.`in`

import io.hhplus.tdd.domain.point.PointHistory

interface FindPointHistoryInPort {
    fun findAllPointHistoryBy(userId: Long): List<PointHistory>
}

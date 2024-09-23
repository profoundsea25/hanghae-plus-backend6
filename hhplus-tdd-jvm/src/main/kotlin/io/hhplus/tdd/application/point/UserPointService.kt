package io.hhplus.tdd.application.point

import io.hhplus.tdd.domain.point.UserPoint
import io.hhplus.tdd.domain.point.`in`.FindUserPointInPort
import io.hhplus.tdd.domain.point.out.FindUserPointOutPort
import org.springframework.stereotype.Service

@Service
class UserPointService(
    private val findUserPointOutPort: FindUserPointOutPort
) : FindUserPointInPort {
    override fun findBy(id: Long): UserPoint {
        return findUserPointOutPort.findBy(id)
    }
}

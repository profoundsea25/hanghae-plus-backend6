package io.hhplus.tdd.application.point

import io.hhplus.tdd.domain.point.PointHistory
import io.hhplus.tdd.domain.point.UserPoint
import io.hhplus.tdd.domain.point.`in`.ChargeUserPointInPort
import io.hhplus.tdd.domain.point.`in`.FindPointHistoryInPort
import io.hhplus.tdd.domain.point.`in`.FindUserPointInPort
import io.hhplus.tdd.domain.point.out.ChargeUserPointOutPort
import io.hhplus.tdd.domain.point.out.FindPointHistoryOutPort
import io.hhplus.tdd.domain.point.out.FindUserPointOutPort
import io.hhplus.tdd.domain.point.out.SaveChargeUserPointHistoryOutPort
import org.springframework.stereotype.Service

@Service
class PointService(
    private val findUserPointOutPort: FindUserPointOutPort,
    private val chargeUserPointOutPort: ChargeUserPointOutPort,
    private val saveChargeUserPointHistoryOutPort: SaveChargeUserPointHistoryOutPort,
    private val findPointHistoryOutPort: FindPointHistoryOutPort,
) : FindUserPointInPort,
    ChargeUserPointInPort,
    FindPointHistoryInPort {
    override fun findBy(id: Long): UserPoint {
        return findUserPointOutPort.findBy(id)
    }

    override fun charge(
        id: Long,
        amount: Long,
    ): UserPoint {
        return chargeUserPointOutPort.charge(
            id = id,
            amount = amount,
        )
            .also {
                saveChargeUserPointHistoryOutPort.saveChargePointHistory(
                    id = it.id,
                    amount = it.point,
                    updateMillis = it.updateMillis,
                )
            }
    }

    override fun findAllBy(userId: Long): List<PointHistory> {
        return findPointHistoryOutPort.findAllBy(userId)
    }
}

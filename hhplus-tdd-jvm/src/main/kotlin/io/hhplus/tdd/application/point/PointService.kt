package io.hhplus.tdd.application.point

import io.hhplus.tdd.domain.point.PointHistory
import io.hhplus.tdd.domain.point.TransactionType
import io.hhplus.tdd.domain.point.UserPoint
import io.hhplus.tdd.domain.point.`in`.ChargeUserPointInPort
import io.hhplus.tdd.domain.point.`in`.FindPointHistoryInPort
import io.hhplus.tdd.domain.point.`in`.FindUserPointInPort
import io.hhplus.tdd.domain.point.`in`.UseUserPointInPort
import io.hhplus.tdd.domain.point.out.FindPointHistoryOutPort
import io.hhplus.tdd.domain.point.out.FindUserPointOutPort
import io.hhplus.tdd.domain.point.out.SavePointHistoryOutPort
import io.hhplus.tdd.domain.point.out.SaveUserPointOutPort
import org.springframework.stereotype.Service

@Service
class PointService(
    private val findUserPointOutPort: FindUserPointOutPort,
    private val saveUserPointOutPort: SaveUserPointOutPort,
    private val savePointHistoryOutPort: SavePointHistoryOutPort,
    private val findPointHistoryOutPort: FindPointHistoryOutPort,
) : FindUserPointInPort,
    ChargeUserPointInPort,
    FindPointHistoryInPort,
    UseUserPointInPort {
    override fun findUserPointBy(userId: Long): UserPoint {
        return findUserPointOutPort.findBy(userId)
    }

    override fun charge(
        id: Long,
        amount: Long,
    ): UserPoint {
        return findUserPointOutPort.findBy(id)
            .let {
                saveUserPointOutPort.save(
                    id = it.id,
                    amount = it.point.plus(amount),
                )
            }
            .also {
                savePointHistoryOutPort.savePointHistory(
                    id = it.id,
                    amount = amount,
                    transactionType = TransactionType.CHARGE,
                    updateMillis = it.updateMillis,
                )
            }
    }

    override fun findAllPointHistoryBy(userId: Long): List<PointHistory> {
        return findPointHistoryOutPort.findAllBy(userId)
    }

    override fun use(
        id: Long,
        amount: Long,
    ): UserPoint {
        return findUserPointOutPort.findBy(id)
            .let {
                saveUserPointOutPort.save(
                    id = it.id,
                    amount = it.point.minus(amount),
                )
            }
            .also {
                savePointHistoryOutPort.savePointHistory(
                    id = it.id,
                    amount = amount,
                    transactionType = TransactionType.USE,
                    updateMillis = it.updateMillis,
                )
            }
    }
}

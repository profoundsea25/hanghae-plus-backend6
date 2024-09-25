package io.hhplus.tdd.application.point

import io.hhplus.tdd.domain.point.PointHistory
import io.hhplus.tdd.domain.point.TransactionType
import io.hhplus.tdd.domain.point.UserPoint
import io.hhplus.tdd.domain.point.`in`.ChargeUserPointInPort
import io.hhplus.tdd.domain.point.`in`.FindPointHistoryInPort
import io.hhplus.tdd.domain.point.`in`.FindUserPointInPort
import io.hhplus.tdd.domain.point.`in`.UseUserPointInPort
import io.hhplus.tdd.domain.point.out.*
import org.springframework.stereotype.Service

@Service
class PointService(
    private val findUserPointOutPort: FindUserPointOutPort,
    private val saveUserPointOutPort: SaveUserPointOutPort,
    private val savePointHistoryOutPort: SavePointHistoryOutPort,
    private val findPointHistoryOutPort: FindPointHistoryOutPort,
    private val lockManager: LockManager,
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
        val userPoint: UserPoint = findUserPointOutPort.findBy(id)
        return lockManager.lockWith(userPoint.id) {
            savePointTransactionAndHistory(
                userPoint = userPoint,
                transactionType = TransactionType.CHARGE,
                amount = amount,
            )
        }
    }

    private fun savePointTransactionAndHistory(
        userPoint: UserPoint,
        transactionType: TransactionType,
        amount: Long,
    ): UserPoint {
        return saveUserPointOutPort.save(
            id = userPoint.id,
            amount = userPoint.doTransaction(transactionType, amount).point,
        )
            .also {
                savePointHistoryOutPort.savePointHistory(
                    id = it.id,
                    amount = amount,
                    transactionType = transactionType,
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
        val userPoint: UserPoint = findUserPointOutPort.findBy(id)
        return lockManager.lockWith(userPoint.id) {
            savePointTransactionAndHistory(
                userPoint = userPoint,
                transactionType = TransactionType.USE,
                amount = amount,
            )
        }
    }
}

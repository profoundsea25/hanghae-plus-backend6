package io.hhplus.tdd.application

import io.hhplus.tdd.adapter.point.lock.ReentrantLockManager
import io.hhplus.tdd.adapter.point.persistance.PointHistoryRepository
import io.hhplus.tdd.adapter.point.persistance.UserPointRepository
import io.hhplus.tdd.application.point.PointService
import io.hhplus.tdd.domain.point.PointHistory
import io.hhplus.tdd.domain.point.TransactionType
import io.hhplus.tdd.domain.point.UserPoint
import io.hhplus.tdd.domain.point.out.LockManager
import io.hhplus.tdd.infrastructure.database.PointHistoryTable
import io.hhplus.tdd.infrastructure.database.UserPointTable
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.concurrent.CompletableFuture

class PointServiceConcurrencyTest {
    private lateinit var pointService: PointService

    @BeforeEach
    fun beforeEach() {
        val userPointTable = UserPointTable()
        val pointHistoryTable = PointHistoryTable()
        val userPointRepository = UserPointRepository(userPointTable)
        val pointHistoryRepository = PointHistoryRepository(pointHistoryTable)
        val lockManager: LockManager = ReentrantLockManager()
        pointService =
            PointService(
                findUserPointOutPort = userPointRepository,
                saveUserPointOutPort = userPointRepository,
                savePointHistoryOutPort = pointHistoryRepository,
                findPointHistoryOutPort = pointHistoryRepository,
                lockManager = lockManager,
            )
    }

    @Test
    @DisplayName("동시에 같은 userId로 충전과 사용을 요청할 때, UserPoint 값과 이력 내역이 정확해야 한다.")
    fun shouldSavePointAndHistoryCorrectlyWithSingleUser() {
        val userId: Long = 1
        val userPointAmount = 1_000_000L
        pointService.charge(userId, userPointAmount)

        val actions: MutableList<CompletableFuture<Void>> = mutableListOf()
        repeat(100) {
            actions.add(CompletableFuture.runAsync { pointService.charge(userId, 10) })
            actions.add(CompletableFuture.runAsync { pointService.use(userId, 10) })
        }

        CompletableFuture.allOf(*actions.toTypedArray())
            .join()

        val resultUserPoint: UserPoint = pointService.findUserPointBy(userId)
        val resultPointHistory: List<PointHistory> = pointService.findAllPointHistoryBy(userId)
        assertThat(resultUserPoint.point).isEqualTo(userPointAmount)
        assertThat(resultPointHistory).hasSize(1 + 100 + 100)
        assertThat(resultPointHistory.filter { it.type == TransactionType.CHARGE }).hasSize(1 + 100)
        assertThat(resultPointHistory.filter { it.type == TransactionType.USE }).hasSize(100)
    }

    @Test
    @DisplayName("동시에 여러 id로 충전을 할 때, 정확한 값으로 충전되어야 한다.")
    fun shouldChargeCorrectlyWithMultipleUser() {
        val expectedChargeAmounts: List<Pair<Long, Long>> =
            listOf(
                1L to 10L,
                2L to 20L,
                3L to 30L,
                1L to 40L,
                2L to 50L,
                3L to 60L,
            )

        CompletableFuture.allOf(
            *expectedChargeAmounts
                .map { entry -> CompletableFuture.runAsync { pointService.charge(entry.first, entry.second) } }
                .toTypedArray(),
        ).join()

        assertThat(pointService.findUserPointBy(1L).point).isEqualTo(50L)
        assertThat(pointService.findAllPointHistoryBy(1L)).hasSize(2)
        assertThat(pointService.findUserPointBy(2L).point).isEqualTo(70L)
        assertThat(pointService.findAllPointHistoryBy(2L)).hasSize(2)
        assertThat(pointService.findUserPointBy(3L).point).isEqualTo(90L)
        assertThat(pointService.findAllPointHistoryBy(3L)).hasSize(2)
    }
}

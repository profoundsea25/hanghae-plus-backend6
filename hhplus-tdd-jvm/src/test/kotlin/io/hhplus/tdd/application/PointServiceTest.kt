package io.hhplus.tdd.application

import io.hhplus.tdd.adapter.point.persistance.PointHistoryRepository
import io.hhplus.tdd.adapter.point.persistance.UserPointRepository
import io.hhplus.tdd.application.point.PointService
import io.hhplus.tdd.domain.point.PointHistory
import io.hhplus.tdd.domain.point.TransactionType
import io.hhplus.tdd.domain.point.UserPoint
import io.hhplus.tdd.fixture.USER_POINT_1
import io.hhplus.tdd.fixture.USER_POINT_2
import io.hhplus.tdd.fixture.USER_POINT_3
import io.hhplus.tdd.infrastructure.database.PointHistoryTable
import io.hhplus.tdd.infrastructure.database.UserPointTable
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class PointServiceTest {
    private val userPointTable = UserPointTable()
    private val pointHistoryTable = PointHistoryTable()
    private val userPointRepository = UserPointRepository(userPointTable)
    private val pointHistoryRepository = PointHistoryRepository(pointHistoryTable)
    private val pointService =
        PointService(
            findUserPointOutPort = userPointRepository,
            saveUserPointOutPort = userPointRepository,
            savePointHistoryOutPort = pointHistoryRepository,
            findPointHistoryOutPort = pointHistoryRepository,
        )

    companion object {
        @JvmStatic
        private fun userPointFixtures(): Stream<Arguments> =
            Stream.of(
                Arguments.of(USER_POINT_1),
                Arguments.of(USER_POINT_2),
                Arguments.of(USER_POINT_3),
            )
    }

    @BeforeEach
    fun beforeEach() {
        pointService.charge(USER_POINT_1.id, USER_POINT_1.point)
        pointService.charge(USER_POINT_2.id, USER_POINT_2.point)
        pointService.charge(USER_POINT_3.id, USER_POINT_3.point)
    }

    @ParameterizedTest
    @MethodSource("userPointFixtures")
    @DisplayName("데이터베이스에 존재하는 UserPoint를 조회했을 때, 알맞은 id와 point를 반환한다.")
    fun shouldReturnCorrectUserPointWithKnownUser(userPoint: UserPoint) {
        val actualUserPoint: UserPoint = pointService.findUserPointBy(userPoint.id)
        val actualPointHistory: PointHistory = pointService.findAllPointHistoryBy(userPoint.id).first()

        assertThat(actualUserPoint.id).isEqualTo(userPoint.id)
        assertThat(actualUserPoint.point).isEqualTo(userPoint.point)
        assertThat(actualPointHistory.id).isEqualTo(userPoint.id)
        assertThat(actualPointHistory.amount).isEqualTo(userPoint.point)
        assertThat(actualPointHistory.type).isEqualTo(TransactionType.CHARGE)
        assertThat(actualPointHistory.timeMillis).isEqualTo(actualUserPoint.updateMillis)
    }

    @Test
    @DisplayName("데이터베이스에 존재하지 않는 UserPoint를 조회하면, point는 0이다")
    fun shouldReturn0PointWithUnknownUser() {
        val result = pointService.findUserPointBy(Long.MAX_VALUE)

        assertThat(result.id).isEqualTo(Long.MAX_VALUE)
        assertThat(result.point).isEqualTo(0)
    }

    @Test
    @DisplayName("UserPoint를 충전하면, 충전된 UserPoint 값과 PointHistory를 저장한다.")
    fun shouldReturnCorrectUserPointWhenCharged() {
        val inputUserId: Long = 1
        val inputAmount: Long = 50

        val actualUserPoint: UserPoint = pointService.charge(inputUserId, inputAmount)
        val actualPointHistories: List<PointHistory> = pointService.findAllPointHistoryBy(inputUserId)

        assertThat(actualUserPoint.id).isEqualTo(inputUserId)
        assertThat(actualUserPoint.point).isEqualTo(USER_POINT_1.point + inputAmount)
        assertThat(actualPointHistories).hasSize(2)
        assertThat(actualPointHistories.last().userId).isEqualTo(inputUserId)
        assertThat(actualPointHistories.last().amount).isEqualTo(inputAmount)
        assertThat(actualPointHistories.last().type).isEqualTo(TransactionType.CHARGE)
        assertThat(actualPointHistories.last().timeMillis).isEqualTo(actualUserPoint.updateMillis)
    }
}

package io.hhplus.tdd.application

import io.hhplus.tdd.adapter.point.persistance.UserPointRepository
import io.hhplus.tdd.application.point.PointService
import io.hhplus.tdd.domain.point.UserPoint
import io.hhplus.tdd.fixture.USER_POINT_1
import io.hhplus.tdd.fixture.USER_POINT_2
import io.hhplus.tdd.fixture.USER_POINT_3
import io.hhplus.tdd.infrastructure.database.UserPointTable
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class PointServiceTest {
    private val pointService = PointService(UserPointRepository(userPointTable))

    companion object {
        private val userPointTable = UserPointTable()

        @JvmStatic
        @BeforeAll
        fun setUp() {
            userPointTable.insertOrUpdate(USER_POINT_1.id, USER_POINT_1.point)
            userPointTable.insertOrUpdate(USER_POINT_2.id, USER_POINT_2.point)
            userPointTable.insertOrUpdate(USER_POINT_3.id, USER_POINT_3.point)
        }

        @JvmStatic
        private fun userPointFixtures(): Stream<Arguments> =
            Stream.of(
                Arguments.of(USER_POINT_1),
                Arguments.of(USER_POINT_2),
                Arguments.of(USER_POINT_3),
            )
    }

    @ParameterizedTest
    @MethodSource("userPointFixtures")
    @DisplayName("데이터베이스에 존재하는 UserPoint를 조회했을 때, 알맞은 id와 point를 반환한다.")
    fun shouldReturnCorrectUserPointWithKnownUser(userPoint: UserPoint) {
        val result = pointService.findBy(userPoint.id)
        assertThat(result.id).isEqualTo(userPoint.id)
        assertThat(result.point).isEqualTo(userPoint.point)
    }

    @Test
    @DisplayName("데이터베이스에 존재하지 않는 UserPoint를 조회하면, point는 0이다")
    fun shouldReturn0PointWithUnknownUser() {
        val result = pointService.findBy(Long.MAX_VALUE)
        assertThat(result.id).isEqualTo(Long.MAX_VALUE)
        assertThat(result.point).isEqualTo(0)
    }
}

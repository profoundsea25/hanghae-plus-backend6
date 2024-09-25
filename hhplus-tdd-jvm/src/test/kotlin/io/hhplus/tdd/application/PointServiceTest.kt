package io.hhplus.tdd.application

import io.hhplus.tdd.any
import io.hhplus.tdd.application.point.PointService
import io.hhplus.tdd.domain.point.PointHistory
import io.hhplus.tdd.domain.point.TransactionType
import io.hhplus.tdd.domain.point.exception.BusinessLogicException
import io.hhplus.tdd.domain.point.exception.ErrorMessage
import io.hhplus.tdd.domain.point.out.FindPointHistoryOutPort
import io.hhplus.tdd.domain.point.out.FindUserPointOutPort
import io.hhplus.tdd.domain.point.out.SavePointHistoryOutPort
import io.hhplus.tdd.domain.point.out.SaveUserPointOutPort
import io.hhplus.tdd.fake.FakeLockManger
import io.hhplus.tdd.fixture.USER_POINT_1
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*

class PointServiceTest {
    private val findUserPointOutPort = mock(FindUserPointOutPort::class.java)
    private val saveUserPointOutPort = mock(SaveUserPointOutPort::class.java)
    private val savePointHistoryOutPort = mock(SavePointHistoryOutPort::class.java)
    private val findPointHistoryOutPort = mock(FindPointHistoryOutPort::class.java)
    private lateinit var pointService: PointService

    @BeforeEach
    fun beforeEach() {
        pointService =
            PointService(
                findUserPointOutPort = findUserPointOutPort,
                saveUserPointOutPort = saveUserPointOutPort,
                savePointHistoryOutPort = savePointHistoryOutPort,
                findPointHistoryOutPort = findPointHistoryOutPort,
                lockManager = FakeLockManger(),
            )
    }

    @Test
    @DisplayName("정상적인 포인트 충전을 수행하면, UserPoint와 PointHistory를 저장 메서드를 한 번씩 호출한다.")
    fun shouldCallOnceSavePointMethodAndSaveHistoryMethodWhenValidCharging() {
        val inputChargeAmount = 10L
        val expected = USER_POINT_1.copy(point = USER_POINT_1.point + inputChargeAmount)

        `when`(findUserPointOutPort.findBy(anyLong()))
            .thenReturn(USER_POINT_1)
        `when`(
            saveUserPointOutPort.save(
                anyLong(),
                anyLong(),
            ),
        )
            .thenReturn(expected)
        `when`(
            savePointHistoryOutPort.savePointHistory(
                anyLong(),
                anyLong(),
                any(TransactionType::class.java),
                anyLong(),
            ),
        )
            .thenReturn(
                PointHistory(
                    id = 1,
                    userId = expected.id,
                    type = TransactionType.CHARGE,
                    amount = inputChargeAmount,
                    timeMillis = expected.updateMillis,
                ),
            )

        pointService.charge(USER_POINT_1.id, inputChargeAmount)

        verify(saveUserPointOutPort, times(1))
            .save(
                id = expected.id,
                amount = expected.point,
            )
        verify(savePointHistoryOutPort, times(1))
            .savePointHistory(
                id = expected.id,
                amount = inputChargeAmount,
                transactionType = TransactionType.CHARGE,
                updateMillis = expected.updateMillis,
            )
    }

    @Test
    @DisplayName("정상적인 포인트 사용을 수행하면, UserPoint와 PointHistory를 저장 메서드를 한 번씩 호출한다.")
    fun shouldCallOnceSavePointMethodAndSaveHistoryMethodWhenValidUsing() {
        val inputUseAmount = 10L
        val expected = USER_POINT_1.copy(point = USER_POINT_1.point - inputUseAmount)

        `when`(findUserPointOutPort.findBy(anyLong()))
            .thenReturn(USER_POINT_1)
        `when`(
            saveUserPointOutPort.save(
                anyLong(),
                anyLong(),
            ),
        )
            .thenReturn(expected)
        `when`(
            savePointHistoryOutPort.savePointHistory(
                anyLong(),
                anyLong(),
                any(TransactionType::class.java),
                anyLong(),
            ),
        )
            .thenReturn(
                PointHistory(
                    id = 1,
                    userId = expected.id,
                    type = TransactionType.USE,
                    amount = inputUseAmount,
                    timeMillis = expected.updateMillis,
                ),
            )

        pointService.use(USER_POINT_1.id, inputUseAmount)

        verify(saveUserPointOutPort, times(1))
            .save(
                id = expected.id,
                amount = expected.point,
            )
        verify(savePointHistoryOutPort, times(1))
            .savePointHistory(
                id = expected.id,
                amount = inputUseAmount,
                transactionType = TransactionType.USE,
                updateMillis = expected.updateMillis,
            )
    }

    @Test
    @DisplayName("5000000 이상 충전을 하려고 하면, UserPoint 저장 메서드와 PointHistory 저장 메서드가 호출되지 않는다.")
    fun shouldNotCallSaveUserPointMethodAndSavePointHistoryMethodWhenOverCharging() {
        val inputChargeAmount = 5_000_000L

        `when`(findUserPointOutPort.findBy(anyLong()))
            .thenReturn(USER_POINT_1)

        val exception =
            assertThrows<BusinessLogicException> {
                pointService.charge(USER_POINT_1.id, inputChargeAmount)
            }

        assertThat(exception.errorMessage).isEqualTo(ErrorMessage.OVER_CHARGING_POINT)
        verify(saveUserPointOutPort, never())
            .save(
                id = anyLong(),
                amount = anyLong(),
            )
        verify(savePointHistoryOutPort, never())
            .savePointHistory(
                id = anyLong(),
                amount = anyLong(),
                transactionType = any(TransactionType::class.java),
                updateMillis = anyLong(),
            )
    }

    @Test
    @DisplayName("남은 포인트가 0보다 작게 사용하려고 하면, UserPoint 저장 메서드와 PointHistory 저장 메서드가 호출되지 않는다.")
    fun shouldNotCallSaveUserPointMethodAndSavePointHistoryMethodWhenOverUsing() {
        val inputUseAmount = 100L

        `when`(findUserPointOutPort.findBy(anyLong()))
            .thenReturn(USER_POINT_1)

        val exception =
            assertThrows<BusinessLogicException> {
                pointService.use(USER_POINT_1.id, inputUseAmount)
            }

        assertThat(exception.errorMessage).isEqualTo(ErrorMessage.OVER_USING_POINT)
        verify(saveUserPointOutPort, never())
            .save(
                id = anyLong(),
                amount = anyLong(),
            )
        verify(savePointHistoryOutPort, never())
            .savePointHistory(
                id = anyLong(),
                amount = anyLong(),
                transactionType = any(TransactionType::class.java),
                updateMillis = anyLong(),
            )
    }
}

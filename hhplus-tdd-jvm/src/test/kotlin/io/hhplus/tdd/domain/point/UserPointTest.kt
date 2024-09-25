package io.hhplus.tdd.domain.point

import io.hhplus.tdd.domain.point.exception.BusinessLogicException
import io.hhplus.tdd.domain.point.exception.ErrorMessage
import io.hhplus.tdd.fixture.USER_POINT_1
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrowsExactly
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class UserPointTest {
    @ParameterizedTest
    @DisplayName("UserPoint 값이 5_000_000L보다 작거나 0 보다 크면, Exception이 발생하지 않는다.")
    @CsvSource("5000000,0,1,4999999")
    fun shouldNotThrowExceptionWhenHasValidPoint(point: Long) {
        assertDoesNotThrow { UserPoint(1, point, 0) }
    }

    @ParameterizedTest
    @DisplayName("UserPoint 값이 5_000_000L보다 크거나 0 보다 작으면, Exception이 발생한다.")
    @CsvSource("5000001,-1,-10,10000000")
    fun shouldThrowExceptionWhenOverChargedOrOverUsed(point: Long) {
        assertThrowsExactly(BusinessLogicException::class.java) {
            UserPoint(1, point, 0)
        }
    }

    @ParameterizedTest
    @DisplayName("UserPoint 값이 5_000_000L보다 작게 충전하려고 하면, Exception이 발생하지 않는다.")
    @CsvSource("10,4999980,4999990")
    fun shouldNotThrowExceptionWhenValidCharging(amount: Long) {
        assertDoesNotThrow { USER_POINT_1.doTransaction(TransactionType.CHARGE, amount) }
    }

    @ParameterizedTest
    @DisplayName("UserPoint 값이 5_000_000L보다 크게 충전하려고 하면, Exception이 발생한다.")
    @CsvSource("4999991,4999992,5000000")
    fun shouldThrowExceptionWhenOverCharging(amount: Long) {
        val exception =
            assertThrowsExactly(BusinessLogicException::class.java) {
                USER_POINT_1.doTransaction(TransactionType.CHARGE, amount)
            }
        assertThat(exception.errorMessage).isEqualTo(ErrorMessage.OVER_CHARGING_POINT)
    }

    @ParameterizedTest
    @DisplayName("남은 UserPoint 값이 0보다 크게 사용하려고 하면, Exception이 발생하지 않는다.")
    @CsvSource("1,2,10")
    fun shouldNotThrowExceptionWhenValidUsing(amount: Long) {
        assertDoesNotThrow { USER_POINT_1.doTransaction(TransactionType.USE, amount) }
    }

    @ParameterizedTest
    @DisplayName("남은 UserPoint 값이 0보다 작게 사용하려고 하면, Exception이 발생한다.")
    @CsvSource("11,12,100")
    fun shouldThrowExceptionWhenOverUsing(amount: Long) {
        val exception =
            assertThrowsExactly(BusinessLogicException::class.java) {
                USER_POINT_1.doTransaction(TransactionType.USE, amount)
            }
        assertThat(exception.errorMessage).isEqualTo(ErrorMessage.OVER_USING_POINT)
    }
}

package y.june.prestudy.auth.security.jwt

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import y.june.prestudy.auth.fixture.fakeMember
import y.june.prestudy.auth.fixture.jwtProviderFixture
import y.june.prestudy.common.api.ResponseCode
import y.june.prestudy.common.exception.BadRequestException
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class JwtProviderTest : BehaviorSpec({
    val jwtProvider = jwtProviderFixture

    Given("토큰이 만료시간을 지나지 않고, 정상적으로 생성되었다면,") {
        val token = jwtProvider.generate(fakeMember)

        When("토큰을 복호화했을 때,") {
            val result = jwtProvider.getUsername(token)

            Then("username을 정상적으로 반환할 수 있다.") {
                result shouldBe fakeMember.username
            }
        }
    }

    Given("토큰이 만료시간을 지난 경우,") {
        val token = jwtProvider.generate(
            member = fakeMember,
            now = ZonedDateTime.of(
                LocalDateTime.of(2024, 1, 1, 0, 0),
                ZoneId.of("Asia/Seoul"),
            ),
        )

        When("토큰을 복호화하면,") {
            Then("오류가 발생한다.") {
                val exception = shouldThrowExactly<BadRequestException> {
                    jwtProvider.getUsername(token)
                }
                exception.status shouldBe ResponseCode.INVALID_JWT
            }
        }
    }

})

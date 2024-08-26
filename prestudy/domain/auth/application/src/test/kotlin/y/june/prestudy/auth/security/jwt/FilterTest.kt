package y.june.prestudy.auth.security.jwt

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import y.june.prestudy.auth.BEARER_PREFIX
import y.june.prestudy.common.api.ResponseCode
import y.june.prestudy.common.exception.BadRequestException

class FilterTest : BehaviorSpec({

    Given("Authorization 토큰이 주어졌을 때,") {
        val token = "testtoken"
        val tokenWithBearer = BEARER_PREFIX + token

        When("올바른 Bearer 형식일 경우,") {
            val result = extractToken(tokenWithBearer)

            Then("토큰을 정상 추출할 수 있다.") {
                result shouldBe token
            }
        }

        When("토큰이 null인 경우,") {
            Then("UNAUTHORIZED Exception이 발생한다.") {
                val exception = shouldThrowExactly<BadRequestException> {
                    extractToken(null)
                }
                exception.status shouldBe ResponseCode.UNAUTHORIZED
            }
        }

        When("토큰이 'Bearer '로 시작하지 않는 경우") {
            Then("UNAUTHORIZED Exception이 발생한다.") {
                val exception = shouldThrowExactly<BadRequestException> {
                    extractToken(null)
                }
                exception.status shouldBe ResponseCode.UNAUTHORIZED
            }
        }
    }

})

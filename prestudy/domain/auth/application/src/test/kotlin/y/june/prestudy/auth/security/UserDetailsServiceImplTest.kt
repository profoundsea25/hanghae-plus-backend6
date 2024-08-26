package y.june.prestudy.auth.security

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import y.june.prestudy.auth.fixture.fakeLoadMemberOutPort
import y.june.prestudy.auth.fixture.fakeUsername
import y.june.prestudy.common.api.ResponseCode
import y.june.prestudy.common.exception.BadRequestException

class UserDetailsServiceImplTest : BehaviorSpec({
    val userDetailsServiceImpl = UserDetailsServiceImpl(fakeLoadMemberOutPort)

    Given("사용자 이름을 검색했을 때,") {
        val username = fakeUsername + "1"

        When("없는 사용자 이름인 경우,") {
            Then("Exception이 발생한다.") {
                val exception = shouldThrowExactly<BadRequestException> {
                    userDetailsServiceImpl.loadUserByUsername(username)
                }
                exception.status shouldBe ResponseCode.NOT_FOUND_USER
            }
        }
    }
})

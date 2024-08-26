package y.june.prestudy.auth.service

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import y.june.prestudy.auth.fixture.*
import y.june.prestudy.auth.model.Role
import y.june.prestudy.auth.port.`in`.LoginCommand
import y.june.prestudy.auth.port.`in`.SignUpCommand
import y.june.prestudy.common.api.ResponseCode
import y.june.prestudy.common.exception.BadRequestException

class AuthServiceTest : BehaviorSpec({
    val authService = AuthService(
        saveMemberOutPort = fakeSaveMemberOutPort,
        loadMemberOutPort = fakeLoadMemberOutPort,
        jwtProvider = jwtProviderFixture,
        passwordEncoder = fakePasswordEncoder,
    )

    Given("회원 가입 시,") {
        When("중복된 사용자 이름이 있다면,") {
            val command = SignUpCommand(
                username = fakeUsername,
                password = fakePassword,
                role = Role.MEMBER,
            )

            Then("가입에 실패한다.") {
                val exception = shouldThrowExactly<BadRequestException> {
                    authService.signUp(command)
                }
                exception.status shouldBe ResponseCode.DUPLICATED_USERNAME
            }
        }
    }

    Given("로그인 시,") {
        When("존재하지 않는 사용자 이름이면,") {
            val command = LoginCommand(
                username = fakeUsername + "1",
                password = fakePassword,
            )

            Then("로그인에 실패한다.") {
                val exception = shouldThrowExactly<BadRequestException> {
                    authService.login(command)
                }
                exception.status shouldBe ResponseCode.LOGIN_FAILED_INVALID_USERNAME
            }
        }

        When("존재하지 않는 사용자 이름이면,") {
            val command = LoginCommand(
                username = fakeUsername,
                password = fakePassword + "1",
            )

            Then("로그인에 실패한다.") {
                val exception = shouldThrowExactly<BadRequestException> {
                    authService.login(command)
                }
                exception.status shouldBe ResponseCode.LOGIN_FAILED_INVALID_PASSWORD
            }
        }
    }
})

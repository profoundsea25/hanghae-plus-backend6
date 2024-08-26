package y.june.prestudy.auth.port.`in`

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import jakarta.validation.Validation
import jakarta.validation.Validator

class LoginCommandTest : BehaviorSpec({
    lateinit var validator: Validator

    beforeEach {
        validator = Validation.buildDefaultValidatorFactory().validator
    }

    Given("로그인 입력값이 모두 주어졌을 때,") {
        When("조건에 맞는 입력값이 주어진 경우 검증하면,") {
            val input = LoginCommand("test", "TEst1234")
            Then("성공한다.") {
                validator.validate(input).map { it.message } shouldBe emptyList()
            }
        }

        When("사용자 이름과 비밀번호에 특수문자가 포함된 경우 검증하면,") {
            val input = LoginCommand("test!", "TEst1234!")
            val expected = listOf(USERNAME_VIOLATION_MESSAGE, PASSWORD_VIOLATION_MESSAGE)
            Then("실패하며, 2개의 오류 메시지를 가진다.") {
                validator.validate(input)
                    .map { it.message } shouldContainExactlyInAnyOrder expected
            }
        }
    }

    Given("로그인 입력값으로 사용자 이름(username)이 주어졌을 때,") {
        When("길이가 4자 미만이거나 10자 초과인 경우 검증하면,") {
            Then("실패한다.") {
                forAll(
                    *listOf("123", "12", "12345678901")
                        .map { LoginCommand(it, "testtest") }
                        .map { row(it) }
                        .toTypedArray()
                ) { dto ->
                    validator.validate(dto)
                        .forEach { violation -> violation.message shouldBe USERNAME_VIOLATION_MESSAGE }
                }
            }
        }

        When("알파벳 소문자나 숫자 이외의 문자가 들어간 경우 검증하면,") {
            Then("실패한다.") {
                forAll(
                    *listOf("123A", "12aB", "D12345678a")
                        .map { LoginCommand(it, "testtest") }
                        .map { row(it) }
                        .toTypedArray()
                ) { dto ->
                    validator.validate(dto)
                        .forEach { violation -> violation.message shouldBe USERNAME_VIOLATION_MESSAGE }
                }
            }
        }
    }

    Given("로그인 입력값으로 비밀번호(password)가 주어졌을 때,") {
        When("길이가 8자 미만이거나 15자 초과인 경우 검증하면,") {
            Then("실패한다.") {
                forAll(
                    *listOf("1234567", "12", "1234567890123456")
                        .map { LoginCommand("test", it) }
                        .map { row(it) }
                        .toTypedArray()
                ) { dto ->
                    validator.validate(dto)
                        .forEach { violation -> violation.message shouldBe PASSWORD_VIOLATION_MESSAGE }
                }
            }
        }

        When("알파벳 대소문자나 숫자 이외의 문자가 들어간 경우 검증하면,") {
            Then("실패한다.") {
                forAll(
                    *listOf("1234567A!", "D12345678a123#")
                        .map { LoginCommand(it, "testtest") }
                        .map { row(it) }
                        .toTypedArray()
                ) { dto ->
                    validator.validate(dto)
                        .forEach { violation -> violation.message shouldBe USERNAME_VIOLATION_MESSAGE }
                }
            }
        }
    }
})

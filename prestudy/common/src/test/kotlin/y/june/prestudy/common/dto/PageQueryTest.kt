package y.june.prestudy.common.dto

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import y.june.prestudy.common.exception.BadRequestException

class PageQueryTest : BehaviorSpec({
    val fixture = PageQuery(
        pageNo = 1,
        pageSize = 10,
        sort = "DESC",
        orderBy = "",
    )

    Given("PageQuery 객체를 만들 때,") {
        When("pageNo가 0보다 작은 경우,") {
            Then("Exception이 발생한다.") {
                shouldThrowExactly<BadRequestException> {
                    fixture.copy(pageNo = -1)
                }
            }
        }

        When("pageSize가 0보다 같거나 작은 경우,") {
            Then("Exception이 발생한다.") {
                shouldThrowExactly<BadRequestException> {
                    fixture.copy(pageSize = 0)
                }
            }
        }

        When("sort 값이 desc나 asc가 아닌 경우,") {
            Then("Exception이 발생한다.") {
                shouldThrowExactly<BadRequestException> {
                    fixture.copy(sort = "askldjakl")
                }
            }
        }
    }
})
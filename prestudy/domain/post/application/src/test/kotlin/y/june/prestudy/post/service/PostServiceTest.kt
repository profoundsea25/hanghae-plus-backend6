package y.june.prestudy.post.service

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import y.june.prestudy.common.api.ResponseCode
import y.june.prestudy.common.exception.BadRequestException
import y.june.prestudy.post.*
import y.june.prestudy.post.port.`in`.DeletePostCommand

class PostServiceTest : BehaviorSpec({
    val postService = PostService(
        fakeCreatePostOutPort,
        fakeFindOnePostOutPort,
        fakeDeletePostOutPort,
        fakeFindAllPostOutPort,
    )

    Given("게시글 조회 시, 존재하지 않는 게시글 id가 주어지고,") {
        val postId = 123L

        When("그 id로 게시글을 조회하면,") {
            Then("존재하지 않는 게시글 Exception이 발생한다.") {
                val exception = shouldThrowExactly<BadRequestException> {
                    postService.findOne(postId)
                }
                exception.status shouldBe ResponseCode.NOT_FOUND_POST
            }
        }
    }

    Given("게시글 삭제 시,") {
        val command = DeletePostCommand(postId = 0L, password = "")
        
        When("존재하지 않는 게시글 id를 요청하면,") {
            Then("존재하지 않는 게시글 Exception이 발생한다.") {
                val exception = shouldThrowExactly<BadRequestException> {
                    postService.delete(command)
                }
                exception.status shouldBe ResponseCode.NOT_FOUND_POST
            }
        }

        When("게시글 id에 해당하는 게시글 비밀번호가 틀린 경우,") {
            Then("비밀번호 미일치 Exception이 발생한다.") {
                val exception = shouldThrowExactly<BadRequestException> {
                    postService.delete(command.copy(postId = postFixture1.id))
                }
                exception.status shouldBe ResponseCode.INCORRECT_POST_PASSWORD
            }
        }
    }
})

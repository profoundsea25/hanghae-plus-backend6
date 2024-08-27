package y.june.prestudy.post.service

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import y.june.prestudy.common.api.ResponseCode
import y.june.prestudy.common.exception.BadRequestException
import y.june.prestudy.post.fakeCreatePostOutPort
import y.june.prestudy.post.fakeFindOnePostOutPort

class PostServiceTest : BehaviorSpec({
    val postService = PostService(fakeCreatePostOutPort, fakeFindOnePostOutPort)

    Given("존재하지 않는 게시글 id가 주어지고,") {
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
})

package y.june.prestudy.post.model

import y.june.prestudy.common.api.ResponseCode
import y.june.prestudy.common.exception.BadRequestException
import java.time.LocalDateTime

class Post(
    val id: Long = 0,
    val author: String,
    val title: String,
    val password: String,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    fun isMatchedPassword(inputPassword: String) {
        if (this.password != inputPassword) {
            throw BadRequestException(ResponseCode.INCORRECT_POST_PASSWORD)
        }
    }
}

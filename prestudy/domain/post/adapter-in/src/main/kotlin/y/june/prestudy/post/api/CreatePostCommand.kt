package y.june.prestudy.post.api

import y.june.prestudy.post.model.Post
import java.time.LocalDateTime

data class CreatePostCommand(
    val author: String,
    val title: String,
    val password: String,
    val content: String,
) {
    fun toModel(): Post {
        return Post(
            id = null,
            author = this.author,
            title = this.title,
            password = this.password,
            content = this.content,
            createdAt = LocalDateTime.now(),
        )
    }
}

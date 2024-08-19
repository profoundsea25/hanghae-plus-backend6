package y.june.prestudy.post.api

import com.fasterxml.jackson.annotation.JsonFormat
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

data class CreatePostPresentation(
    val id: Long,
    val author: String,
    val title: String,
    val password: String,
    val content: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(post: Post): CreatePostPresentation {
            return CreatePostPresentation(
                id = post.id!!,
                author = post.author,
                title = post.title,
                password = post.password,
                content = post.content,
                createdAt = post.createdAt,
            )
        }
    }
}

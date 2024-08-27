package y.june.prestudy.post.port.`in`

import y.june.prestudy.common.toDefaultFormat
import y.june.prestudy.post.model.Post
import java.time.LocalDateTime

interface CreatePostUseCase {
    fun create(command: CreatePostCommand): CreatePostPresentation
}

data class CreatePostCommand(
    val author: String,
    val title: String,
    val password: String,
    val content: String,
) {
    fun toModel(): Post {
        return Post(
            id = 0,
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
    val createdAt: String,
) {
    companion object {
        fun from(post: Post): CreatePostPresentation {
            return CreatePostPresentation(
                id = post.id!!,
                author = post.author,
                title = post.title,
                password = post.password,
                content = post.content,
                createdAt = post.createdAt.toDefaultFormat()
            )
        }
    }
}

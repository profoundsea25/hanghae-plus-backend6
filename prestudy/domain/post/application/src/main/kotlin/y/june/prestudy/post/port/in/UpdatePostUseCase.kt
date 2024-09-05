package y.june.prestudy.post.port.`in`

import y.june.prestudy.common.toDefaultFormat
import y.june.prestudy.post.model.Post
import java.time.LocalDateTime

interface UpdatePostUseCase {
    fun update(command: UpdatePostCommand): UpdatePostPresentation
}

data class UpdatePostCommand(
    val postId: Long,
    val author: String,
    val title: String,
    val password: String,
    val content: String,
) {
    fun toModel(createdAt: LocalDateTime): Post {
        return Post(
            id = this.postId,
            author = this.author,
            title = this.title,
            password = this.password,
            content = this.content,
            createdAt = createdAt,
            updatedAt = LocalDateTime.now(),
        )
    }
}

data class UpdatePostPresentation(
    val id: Long,
    val author: String,
    val title: String,
    val content: String,
    val createdAt: String,
    val updatedAt: String,
) {
    companion object {
        fun from(post: Post): UpdatePostPresentation {
            return UpdatePostPresentation(
                id = post.id,
                author = post.author,
                title = post.title,
                content = post.content,
                createdAt = post.createdAt.toDefaultFormat(),
                updatedAt = post.updatedAt.toDefaultFormat(),
            )
        }
    }
}

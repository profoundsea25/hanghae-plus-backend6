package y.june.prestudy.post.port.`in`

import y.june.prestudy.common.toDefaultFormat
import y.june.prestudy.post.model.Post

interface FindOnePostUseCase {
    fun findOne(postId: Long): FindOnePostPresentation
}

data class FindOnePostPresentation(
    val postId: Long,
    val author: String,
    val title: String,
    val content: String,
    val createdAt: String,
) {
    companion object {
        fun from(post: Post): FindOnePostPresentation {
            return FindOnePostPresentation(
                postId = post.id,
                author = post.author,
                title = post.title,
                content = post.content,
                createdAt = post.createdAt.toDefaultFormat(),
            )
        }
    }
}

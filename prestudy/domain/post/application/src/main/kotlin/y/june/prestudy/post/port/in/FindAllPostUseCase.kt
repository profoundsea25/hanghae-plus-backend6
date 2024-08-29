package y.june.prestudy.post.port.`in`

import y.june.prestudy.common.dto.PageQuery
import y.june.prestudy.common.dto.PageWrapper
import y.june.prestudy.common.toDefaultFormat
import y.june.prestudy.post.model.Post

interface FinAllPostUseCase {
    fun findAll(query: PageQuery): PageWrapper<PostPresentation>
}

data class PostPresentation(
    val postId: Long,
    val author: String,
    val title: String,
    val content: String,
    val createdAt: String,
) {
    companion object {
        fun from(post: Post): PostPresentation {
            return PostPresentation(
                postId = post.id,
                author = post.author,
                title = post.title,
                content = post.content,
                createdAt = post.createdAt.toDefaultFormat(),
            )
        }
    }
}

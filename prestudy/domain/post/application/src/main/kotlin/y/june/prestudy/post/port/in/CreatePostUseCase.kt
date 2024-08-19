package y.june.prestudy.post.port.`in`

import y.june.prestudy.post.model.Post

interface CreatePostUseCase {
    fun create(post: Post): Post
}

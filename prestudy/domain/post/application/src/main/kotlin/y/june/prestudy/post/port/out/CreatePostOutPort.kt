package y.june.prestudy.post.port.out

import y.june.prestudy.post.model.Post

interface CreatePostOutPort {
    fun create(post: Post): Post
}

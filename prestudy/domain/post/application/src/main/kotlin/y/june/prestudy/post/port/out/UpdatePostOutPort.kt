package y.june.prestudy.post.port.out

import y.june.prestudy.post.model.Post

interface UpdatePostOutPort {
    fun update(post: Post): Post
}

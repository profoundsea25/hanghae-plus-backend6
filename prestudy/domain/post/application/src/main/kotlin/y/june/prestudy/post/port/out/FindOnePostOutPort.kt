package y.june.prestudy.post.port.out

import y.june.prestudy.post.model.Post

interface FindOnePostOutPort {
    fun findOne(postId: Long): Post?
}

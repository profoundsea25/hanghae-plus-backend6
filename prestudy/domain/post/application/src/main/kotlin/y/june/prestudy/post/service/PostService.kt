package y.june.prestudy.post.service

import org.springframework.stereotype.Service
import y.june.prestudy.post.model.Post
import y.june.prestudy.post.port.`in`.CreatePostUseCase
import y.june.prestudy.post.port.out.CreatePostOutPort

@Service
class PostService(
    private val createPostOutPort: CreatePostOutPort
): CreatePostUseCase {
    override fun create(post: Post): Post {
        return createPostOutPort.create(post)
    }
}

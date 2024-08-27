package y.june.prestudy.post.service

import org.springframework.stereotype.Service
import y.june.prestudy.post.port.`in`.CreatePostCommand
import y.june.prestudy.post.port.`in`.CreatePostPresentation
import y.june.prestudy.post.port.`in`.CreatePostUseCase
import y.june.prestudy.post.port.out.CreatePostOutPort

@Service
class PostService(
    private val createPostOutPort: CreatePostOutPort
): CreatePostUseCase {
    override fun create(command: CreatePostCommand): CreatePostPresentation {
        return createPostOutPort.create(command.toModel())
            .let { CreatePostPresentation.from(it) }
    }
}

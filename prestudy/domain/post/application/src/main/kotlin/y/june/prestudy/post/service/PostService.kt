package y.june.prestudy.post.service

import org.springframework.stereotype.Service
import y.june.prestudy.common.api.ResponseCode
import y.june.prestudy.common.exception.BadRequestException
import y.june.prestudy.post.model.Post
import y.june.prestudy.post.port.`in`.*
import y.june.prestudy.post.port.out.CreatePostOutPort
import y.june.prestudy.post.port.out.DeletePostOutPort
import y.june.prestudy.post.port.out.FindOnePostOutPort

@Service
class PostService(
    private val createPostOutPort: CreatePostOutPort,
    private val findOnePostOutPort: FindOnePostOutPort,
    private val deletePostOutPort: DeletePostOutPort,
) : CreatePostUseCase,
    FindOnePostUseCase,
    DeletePostUseCase {
    override fun create(command: CreatePostCommand): CreatePostPresentation {
        return createPostOutPort.create(command.toModel())
            .let { CreatePostPresentation.from(it) }
    }

    override fun findOne(postId: Long): FindOnePostPresentation {
        return findOnePostOutPort.findOne(postId)
            ?.let { FindOnePostPresentation.from(it) }
            ?: throw BadRequestException(ResponseCode.NOT_FOUND_POST)
    }

    override fun delete(command: DeletePostCommand) {
        validatePost(findOnePostOutPort.findOne(command.postId))
            .also { validatePostPassword(it.password, command.password) }
            .also { deletePostOutPort.delete(command.postId) }
    }

    private fun validatePost(found: Post?): Post {
        return found ?: throw BadRequestException(ResponseCode.NOT_FOUND_POST)
    }

    private fun validatePostPassword(foundPassword: String, requestPassword: String) {
        if (foundPassword != requestPassword) {
            throw BadRequestException(ResponseCode.INCORRECT_POST_PASSWORD)
        }
    }
}

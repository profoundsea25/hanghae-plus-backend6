package y.june.prestudy.post.persistence

import org.springframework.stereotype.Repository
import y.june.prestudy.post.model.Post
import y.june.prestudy.post.persistence.entity.PostJpaEntity
import y.june.prestudy.post.port.out.CreatePostOutPort
import y.june.prestudy.post.port.out.FindOnePostOutPort
import kotlin.jvm.optionals.getOrNull

@Repository
class PostRepository(
    private val postJpaRepository: PostJpaRepository
) : CreatePostOutPort, FindOnePostOutPort {
    override fun create(post: Post): Post {
        return PostJpaEntity.from(post)
            .let { postJpaRepository.save(it) }
            .toModel()
    }

    override fun findOne(postId: Long): Post? {
        return postJpaRepository.findById(postId)
            .getOrNull()
            ?.toModel()

    }
}

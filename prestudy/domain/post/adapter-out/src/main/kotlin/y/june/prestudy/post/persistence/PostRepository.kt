package y.june.prestudy.post.persistence

import org.springframework.stereotype.Repository
import y.june.prestudy.post.model.Post
import y.june.prestudy.post.persistence.entity.PostJpaEntity
import y.june.prestudy.post.port.out.CreatePostOutPort

@Repository
class PostRepository(
    private val postJpaRepository: PostJpaRepository
) : CreatePostOutPort {
    override fun create(post: Post): Post {
        return postJpaRepository.save(PostJpaEntity.from(post)).toModel()
    }
}

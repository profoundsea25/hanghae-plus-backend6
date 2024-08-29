package y.june.prestudy.post.persistence

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository
import y.june.prestudy.common.dto.PageQuery
import y.june.prestudy.common.dto.PageWrapper
import y.june.prestudy.post.model.Post
import y.june.prestudy.post.persistence.entity.PostJpaEntity
import y.june.prestudy.post.port.out.CreatePostOutPort
import y.june.prestudy.post.port.out.DeletePostOutPort
import y.june.prestudy.post.port.out.FindAllPostOutPort
import y.june.prestudy.post.port.out.FindOnePostOutPort
import kotlin.jvm.optionals.getOrNull

@Repository
class PostRepository(
    private val postJpaRepository: PostJpaRepository
) : CreatePostOutPort,
    FindOnePostOutPort,
    DeletePostOutPort,
    FindAllPostOutPort {
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

    override fun delete(postId: Long) {
        postJpaRepository.deleteById(postId)
    }

    override fun findAll(query: PageQuery): PageWrapper<Post> {
        return PageRequest.of(
            query.pageNo,
            query.pageSize,
            Sort.by(
                Sort.Direction.fromString(query.sort),
                query.orderBy,
            ),
        )
            .let { postJpaRepository.findAll(it) }
            .run {
                PageWrapper(
                    contents = this.content.map { it.toModel() },
                    pageNo = this.number,
                    pageSize = this.size,
                    hasNext = !this.isLast,
                )
            }
    }
}

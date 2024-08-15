package y.june.prestudy.post.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import y.june.prestudy.post.persistence.entity.PostJpaEntity

@Component
interface PostJpaRepository : JpaRepository<PostJpaEntity, Long> {
}

package y.june.prestudy.auth.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import y.june.prestudy.auth.persistence.entity.MemberJpaEntity

@Component
interface MemberJpaRepository : JpaRepository<MemberJpaEntity, Long> {
    fun findByUsername(username: String): MemberJpaEntity?
}

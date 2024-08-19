package y.june.prestudy.auth.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component

@Component
interface MemberJpaRepository : JpaRepository<MemberJpaEntity, Long> {
    fun findByUsername(username: String): MemberJpaEntity?
}

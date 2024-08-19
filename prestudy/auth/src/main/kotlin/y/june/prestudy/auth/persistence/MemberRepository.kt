package y.june.prestudy.auth.persistence

import org.springframework.stereotype.Repository
import y.june.prestudy.auth.model.Member

@Repository
class MemberRepository(
    private val memberJpaRepository: MemberJpaRepository,
) {
    fun save(member: Member) {
        memberJpaRepository.save(MemberJpaEntity.from(member)).toMember()
    }

    fun findByUsername(username: String): Member? {
        return memberJpaRepository.findByUsername(username)?.toMember()
    }
}

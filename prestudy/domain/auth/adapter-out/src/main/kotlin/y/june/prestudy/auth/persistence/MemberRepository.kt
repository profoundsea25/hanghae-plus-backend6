package y.june.prestudy.auth.persistence

import org.springframework.stereotype.Repository
import y.june.prestudy.auth.model.Member
import y.june.prestudy.auth.persistence.entity.MemberJpaEntity
import y.june.prestudy.auth.port.out.LoadMemberOutPort
import y.june.prestudy.auth.port.out.SaveMemberOutPort

@Repository
class MemberRepository(
    private val memberJpaRepository: MemberJpaRepository,
) : SaveMemberOutPort,
    LoadMemberOutPort {
    override fun save(member: Member) {
        memberJpaRepository.save(MemberJpaEntity.from(member))
    }

    override fun findByUsername(username: String): Member? {
        return memberJpaRepository.findByUsername(username)?.toMember()
    }
}

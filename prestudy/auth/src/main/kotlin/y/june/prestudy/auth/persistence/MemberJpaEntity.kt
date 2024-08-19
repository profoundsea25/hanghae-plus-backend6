package y.june.prestudy.auth.persistence

import jakarta.persistence.*
import y.june.prestudy.auth.model.Member
import y.june.prestudy.auth.model.Role

@Entity
@Table(name = "MEMBER")
class MemberJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val username: String,
    val password: String,
    @Enumerated(EnumType.STRING)
    val role: Role,
) {
    fun toMember(): Member {
        return Member(
            username = this.username,
            password = this.password,
            role = this.role
        )
    }

    companion object {
        fun from(member: Member): MemberJpaEntity {
            return MemberJpaEntity(
                id = null,
                username = member.username,
                password = member.password,
                role = member.role
            )
        }
    }
}

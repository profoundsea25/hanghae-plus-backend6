package y.june.prestudy.auth.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import y.june.prestudy.auth.model.Member
import y.june.prestudy.auth.model.Role

data class UserDetailsImpl(
    @JvmField
    val username: String,
    @JvmField
    val password: String,
    val role: Role,
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role.name))
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    companion object {
        fun from(member: Member): UserDetails {
            return UserDetailsImpl(
                username = member.username,
                password = member.password,
                role = member.role
            )
        }
    }
}

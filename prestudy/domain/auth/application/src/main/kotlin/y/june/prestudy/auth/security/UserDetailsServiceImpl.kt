package y.june.prestudy.auth.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import y.june.prestudy.auth.port.out.LoadMemberOutPort
import y.june.prestudy.common.api.ResponseCode
import y.june.prestudy.common.exception.BadRequestException

@Component
class UserDetailsServiceImpl(
    private val loadMemberOutPort: LoadMemberOutPort
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return UserDetailsImpl.from(
            member = loadMemberOutPort.findByUsername(username)
                ?: throw BadRequestException(ResponseCode.NOT_FOUND_USER)
        )
    }
}

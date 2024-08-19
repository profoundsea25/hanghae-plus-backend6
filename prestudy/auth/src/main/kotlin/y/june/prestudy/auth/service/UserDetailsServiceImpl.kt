package y.june.prestudy.auth.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import y.june.prestudy.auth.persistence.MemberRepository
import y.june.prestudy.common.api.ResponseCode
import y.june.prestudy.common.exception.BadRequestException

@Service
class UserDetailsServiceImpl(
    private val memberRepository: MemberRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return memberRepository.findByUsername(username)
            ?: throw BadRequestException(ResponseCode.NOT_FOUND_USER)
    }
}

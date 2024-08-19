package y.june.prestudy.auth.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import y.june.prestudy.auth.api.SignUpCommand
import y.june.prestudy.auth.model.Member
import y.june.prestudy.auth.persistence.AuthTransactional
import y.june.prestudy.auth.persistence.MemberRepository
import y.june.prestudy.common.api.ResponseCode
import y.june.prestudy.common.exception.BadRequestException

@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
) {
    @AuthTransactional
    fun singUp(command: SignUpCommand) {
        checkDuplicatedUsername(command.username)
        memberRepository.save(toMember(command))
    }

    private fun checkDuplicatedUsername(username: String) {
        if (memberRepository.findByUsername(username) != null) {
            throw BadRequestException(ResponseCode.DUPLICATED_USERNAME)
        }
    }

    private fun toMember(command: SignUpCommand): Member {
        return Member(
            _username = command.username,
            _password = passwordEncoder.encode(command.password),
            role = command.role,
        )
    }
}

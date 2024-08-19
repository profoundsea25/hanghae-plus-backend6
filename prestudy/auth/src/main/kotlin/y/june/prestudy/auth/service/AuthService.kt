package y.june.prestudy.auth.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import y.june.prestudy.auth.api.LoginCommand
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
            username = command.username,
            password = passwordEncoder.encode(command.password),
            role = command.role,
        )
    }

    fun login(command: LoginCommand): String {
        return memberRepository.findByUsername(command.username)
            .let {
                checkValidUsername(it)
                checkValidPassword(inputPassword = command.password, encodedPassword = it?.password.orEmpty())
                jwtService.createToken(it!!)
            }
    }

    private fun checkValidUsername(member: Member?) {
        if (member == null) {
            throw BadRequestException(ResponseCode.LOGIN_FAILED_INVALID_USERNAME_OR_PASSWORD)
        }
    }

    private fun checkValidPassword(inputPassword: String, encodedPassword: String) {
        if (!passwordEncoder.matches(inputPassword, encodedPassword)) {
            throw BadRequestException(ResponseCode.LOGIN_FAILED_INVALID_USERNAME_OR_PASSWORD)
        }
    }
}

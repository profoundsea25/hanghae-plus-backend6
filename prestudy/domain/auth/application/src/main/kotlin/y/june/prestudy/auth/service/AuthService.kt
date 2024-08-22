package y.june.prestudy.auth.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import y.june.prestudy.auth.model.Member
import y.june.prestudy.auth.port.`in`.*
import y.june.prestudy.auth.port.out.LoadMemberOutPort
import y.june.prestudy.auth.port.out.SaveMemberOutPort
import y.june.prestudy.auth.security.jwt.JwtProvider
import y.june.prestudy.common.api.ResponseCode
import y.june.prestudy.common.exception.BadRequestException

@Service
class AuthService(
    private val saveMemberOutPort: SaveMemberOutPort,
    private val loadMemberOutPort: LoadMemberOutPort,
    private val jwtProvider: JwtProvider,
    private val passwordEncoder: PasswordEncoder,
) : SignUpUseCase,
    LoginUseCase {

    override fun singUp(command: SignUpCommand) {
        checkDuplicatedUsername(command.username)
        saveMemberOutPort.save(
            Member(
                id = null,
                username = command.username,
                password = passwordEncoder.encode(command.password),
                role = command.role,
            )
        )
    }

    private fun checkDuplicatedUsername(username: String) {
        if (loadMemberOutPort.findByUsername(username) != null) {
            throw BadRequestException(ResponseCode.DUPLICATED_USERNAME)
        }
    }

    override fun login(command: LoginCommand): String {
        return loadMemberOutPort.findByUsername(command.username)
            .let {
                checkValidUsername(it)
                checkValidPassword(inputPassword = command.password, encodedPassword = it?.password.orEmpty())
                jwtProvider.generate(it!!)
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

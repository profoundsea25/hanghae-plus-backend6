package y.june.prestudy.auth.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import y.june.prestudy.auth.model.Member
import y.june.prestudy.auth.port.`in`.LoginCommand
import y.june.prestudy.auth.port.`in`.LoginUseCase
import y.june.prestudy.auth.port.`in`.SignUpCommand
import y.june.prestudy.auth.port.`in`.SignUpUseCase
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

    override fun signUp(command: SignUpCommand) {
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
        return checkValidUsername(loadMemberOutPort.findByUsername(command.username))
            .also { checkValidPassword(inputPassword = command.password, encodedPassword = it.password) }
            .let { jwtProvider.generate(it) }
    }

    private fun checkValidUsername(member: Member?): Member {
        return member ?: throw BadRequestException(ResponseCode.LOGIN_FAILED_INVALID_USERNAME_OR_PASSWORD)
    }

    private fun checkValidPassword(inputPassword: String, encodedPassword: String) {
        if (!passwordEncoder.matches(inputPassword, encodedPassword)) {
            throw BadRequestException(ResponseCode.LOGIN_FAILED_INVALID_USERNAME_OR_PASSWORD)
        }
    }
}

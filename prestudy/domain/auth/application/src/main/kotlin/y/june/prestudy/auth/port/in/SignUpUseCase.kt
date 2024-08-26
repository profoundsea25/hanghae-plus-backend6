package y.june.prestudy.auth.port.`in`

import jakarta.validation.constraints.Pattern
import y.june.prestudy.auth.model.Role

interface SignUpUseCase {
    fun signUp(command: SignUpCommand)
}

data class SignUpCommand(
    @field:Pattern(regexp = USERNAME_REGEX, message = USERNAME_VIOLATION_MESSAGE)
    val username: String,
    @field:Pattern(regexp = PASSWORD_REGEX, message = PASSWORD_VIOLATION_MESSAGE)
    val password: String,
    val role: Role,
)

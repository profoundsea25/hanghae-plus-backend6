package y.june.prestudy.auth.port.`in`

import jakarta.validation.constraints.Pattern

interface LoginUseCase {
    fun login(command: LoginCommand): String
}

data class LoginCommand(
    @field:Pattern(regexp = USERNAME_REGEX, message = USERNAME_VIOLATION_MESSAGE)
    val username: String,
    @field:Pattern(regexp = PASSWORD_REGEX, message = PASSWORD_VIOLATION_MESSAGE)
    val password: String,
)

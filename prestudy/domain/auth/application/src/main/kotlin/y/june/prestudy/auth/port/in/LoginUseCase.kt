package y.june.prestudy.auth.port.`in`

import jakarta.validation.constraints.Pattern

interface LoginUseCase {
    fun login(command: LoginCommand): String
}

data class LoginCommand(
    @field:Pattern(regexp = "^[a-z0-9]{4,10}$", message = "사용자이름은 4~10자, 알파벳 소문자와 숫자로만 구성할 수 있습니다.")
    val username: String,
    @field:Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "비밀번호는 8~15자, 알파벳 대소문자와 숫자로만 구성할 수 있습니다.")
    val password: String,
)

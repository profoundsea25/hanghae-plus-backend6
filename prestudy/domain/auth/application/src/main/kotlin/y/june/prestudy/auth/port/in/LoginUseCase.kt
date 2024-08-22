package y.june.prestudy.auth.port.`in`

interface LoginUseCase {
    fun login(command: LoginCommand): String
}

data class LoginCommand(
    val username: String,
    val password: String,
)

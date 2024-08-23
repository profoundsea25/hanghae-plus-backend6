package y.june.prestudy.auth.port.`in`

import y.june.prestudy.auth.model.Role

interface SignUpUseCase {
    fun signUp(command: SignUpCommand)
}

data class SignUpCommand(
    val username: String,
    val password: String,
    val role: Role,
)

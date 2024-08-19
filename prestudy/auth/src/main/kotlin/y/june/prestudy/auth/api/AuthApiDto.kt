package y.june.prestudy.auth.api

import y.june.prestudy.auth.model.Role

data class SignUpCommand(
    val username: String,
    val password: String,
    val role: Role,
)

data class LoginCommand(
    val username: String,
    val password: String,
)

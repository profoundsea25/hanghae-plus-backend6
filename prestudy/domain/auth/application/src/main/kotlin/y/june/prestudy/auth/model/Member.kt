package y.june.prestudy.auth.model

class Member(
    val id: Long?,
    val username: String,
    val password: String,
    val role: Role,
)

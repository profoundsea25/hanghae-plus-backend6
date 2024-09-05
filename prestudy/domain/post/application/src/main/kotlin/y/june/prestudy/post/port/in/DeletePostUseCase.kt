package y.june.prestudy.post.port.`in`

interface DeletePostUseCase {
    fun delete(command: DeletePostCommand)
}

data class DeletePostCommand(
    val postId: Long,
    val password: String,
)

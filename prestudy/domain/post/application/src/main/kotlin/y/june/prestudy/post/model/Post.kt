package y.june.prestudy.post.model

import java.time.LocalDateTime

class Post(
    val id: Long = 0,
    val author: String,
    val title: String,
    val password: String,
    val content: String,
    val createdAt: LocalDateTime,
)

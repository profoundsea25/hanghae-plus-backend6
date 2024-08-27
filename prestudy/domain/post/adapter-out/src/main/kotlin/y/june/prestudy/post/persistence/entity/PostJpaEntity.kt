package y.june.prestudy.post.persistence.entity

import jakarta.persistence.*
import y.june.prestudy.post.model.Post
import java.time.LocalDateTime

@Entity
@Table(name = "POST")
class PostJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val author: String,
    val title: String,
    val password: String,
    val content: String,
    val createdAt: LocalDateTime,
) {
    fun toModel(): Post {
        return Post(
            id = this.id,
            author = this.author,
            title = this.title,
            password = this.password,
            content = this.content,
            createdAt = this.createdAt,
        )
    }

    companion object {
        fun from(post: Post): PostJpaEntity {
            return PostJpaEntity(
                id = post.id,
                author = post.author,
                title = post.title,
                password = post.password,
                content = post.content,
                createdAt = post.createdAt,
            )
        }
    }
}

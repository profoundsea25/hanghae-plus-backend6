package y.june.prestudy.post

import y.june.prestudy.post.model.Post
import y.june.prestudy.post.port.out.CreatePostOutPort
import y.june.prestudy.post.port.out.DeletePostOutPort
import y.june.prestudy.post.port.out.FindOnePostOutPort
import java.time.LocalDateTime

val postFixture: Post = Post(
    id = 1,
    author = "TestUser",
    title = "Test Post",
    password = "testtest",
    content = "This is test post.",
    createdAt = LocalDateTime.of(2024, 8, 1, 9, 0)
)

val fakeCreatePostOutPort: CreatePostOutPort = object : CreatePostOutPort {
    override fun create(post: Post): Post {
        return post
    }
}

val fakeFindOnePostOutPort: FindOnePostOutPort = object : FindOnePostOutPort {
    override fun findOne(postId: Long): Post? {
        return if (postId == 1L) postFixture else null
    }
}

val fakeDeletePostOutPort: DeletePostOutPort = object : DeletePostOutPort {
    override fun delete(postId: Long) {
        // do nothing
    }
}

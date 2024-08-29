package y.june.prestudy.post

import y.june.prestudy.common.dto.PageQuery
import y.june.prestudy.common.dto.PageWrapper
import y.june.prestudy.post.model.Post
import y.june.prestudy.post.port.out.CreatePostOutPort
import y.june.prestudy.post.port.out.DeletePostOutPort
import y.june.prestudy.post.port.out.FindAllPostOutPort
import y.june.prestudy.post.port.out.FindOnePostOutPort
import java.time.LocalDateTime

val postFixture1: Post = Post(
    id = 1,
    author = "TestUser1",
    title = "Test Post3",
    password = "testtest1",
    content = "This is test post 1.",
    createdAt = LocalDateTime.of(2024, 8, 1, 9, 0)
)

val fakeCreatePostOutPort: CreatePostOutPort = object : CreatePostOutPort {
    override fun create(post: Post): Post {
        return post
    }
}

val fakeFindOnePostOutPort: FindOnePostOutPort = object : FindOnePostOutPort {
    override fun findOne(postId: Long): Post? {
        return if (postId == 1L) postFixture1 else null
    }
}

val fakeDeletePostOutPort: DeletePostOutPort = object : DeletePostOutPort {
    override fun delete(postId: Long) {
        // do nothing
    }
}

val fakeFindAllPostOutPort: FindAllPostOutPort = object : FindAllPostOutPort {
    override fun findAll(query: PageQuery): PageWrapper<Post> {
        return PageWrapper(
            contents = listOf(postFixture1),
            pageNo = 1,
            pageSize = 10,
            hasNext = false
        )
    }
}

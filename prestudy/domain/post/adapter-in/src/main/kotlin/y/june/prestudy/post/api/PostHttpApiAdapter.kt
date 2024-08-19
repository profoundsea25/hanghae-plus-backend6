package y.june.prestudy.post.api

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import y.june.prestudy.common.api.Response
import y.june.prestudy.common.api.ok
import y.june.prestudy.post.port.`in`.CreatePostUseCase

@RestController
class PostHttpApiAdapter(
    private val createPostUseCase: CreatePostUseCase
) {
    @PostMapping("/v1/post/create")
    fun create(@RequestBody command: CreatePostCommand): Response<CreatePostPresentation> {
        return ok(
            CreatePostPresentation.from(
                createPostUseCase.create(
                    command.toModel()
                )
            )
        )
    }
}

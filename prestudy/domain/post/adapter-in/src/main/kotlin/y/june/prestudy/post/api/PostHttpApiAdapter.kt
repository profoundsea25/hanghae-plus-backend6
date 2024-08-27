package y.june.prestudy.post.api

import org.springframework.web.bind.annotation.*
import y.june.prestudy.common.api.Response
import y.june.prestudy.common.api.ok
import y.june.prestudy.post.port.`in`.*

@RestController
class PostHttpApiAdapter(
    private val createPostUseCase: CreatePostUseCase,
    private val findOnePostUseCase: FindOnePostUseCase,
) {
    @PostMapping("/v1/post/create")
    fun create(@RequestBody command: CreatePostCommand): Response<CreatePostPresentation> {
        return ok(createPostUseCase.create(command))
    }

    @GetMapping("/v1/post/{postId}")
    fun findOne(@PathVariable postId: Long): Response<FindOnePostPresentation> {
        return ok(findOnePostUseCase.findOne(postId))
    }
}

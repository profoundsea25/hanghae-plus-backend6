package y.june.prestudy.post.api

import org.springframework.web.bind.annotation.*
import y.june.prestudy.common.api.Response
import y.june.prestudy.common.api.ok
import y.june.prestudy.common.dto.PageQuery
import y.june.prestudy.common.dto.PageWrapper
import y.june.prestudy.post.port.`in`.*

@RestController
class PostHttpApiAdapter(
    private val createPostUseCase: CreatePostUseCase,
    private val findOnePostUseCase: FindOnePostUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val finAllPostUseCase: FinAllPostUseCase,
    private val updatePostUseCase: UpdatePostUseCase,
) {
    @PostMapping("/v1/post/create")
    fun create(@RequestBody command: CreatePostCommand): Response<CreatePostPresentation> {
        return ok(createPostUseCase.create(command))
    }

    @GetMapping("/v1/post/{postId}")
    fun findOne(@PathVariable postId: Long): Response<FindOnePostPresentation> {
        return ok(findOnePostUseCase.findOne(postId))
    }

    @PostMapping("/v1/post/delete")
    fun delete(@RequestBody command: DeletePostCommand): Response<Unit> {
        return ok(deletePostUseCase.delete(command))
    }

    @GetMapping("/v1/post/all")
    fun findAll(@ModelAttribute pageQuery: PageQuery): Response<PageWrapper<PostPresentation>> {
        return ok(finAllPostUseCase.findAll(pageQuery))
    }

    @PostMapping("/v1/post/update")
    fun update(@RequestBody command: UpdatePostCommand): Response<UpdatePostPresentation> {
        return ok(updatePostUseCase.update(command))
    }
}

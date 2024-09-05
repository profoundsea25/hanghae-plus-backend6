package y.june.prestudy.post.port.out

import y.june.prestudy.common.dto.PageQuery
import y.june.prestudy.common.dto.PageWrapper
import y.june.prestudy.post.model.Post

interface FindAllPostOutPort {
    fun findAll(query: PageQuery): PageWrapper<Post>
}

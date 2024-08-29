package y.june.prestudy.common.dto

import y.june.prestudy.common.exception.BadRequestException

data class PageQuery(
    val pageNo: Int,
    val pageSize: Int,
    val sort: String,
    val orderBy: String,
) {
    companion object {
        private val directionSet = setOf("DESC", "ASC")
    }

    init {
        if (pageNo < 0) {
            throw BadRequestException(message = "pageNo는 0보다 커야합니다.")
        }

        if (pageSize <= 0) {
            throw BadRequestException(message = "pageSize는 0보다 커야합니다.")
        }

        if (sort.uppercase() !in directionSet) {
            throw BadRequestException(message = "sort는 DESC 혹은 ASC여야 합니다.")
        }
    }
}

data class PageWrapper<T>(
    val contents: List<T>,
    val pageNo: Int,
    val pageSize: Int,
    val hasNext: Boolean,
) {
    fun <M> mapResult(mapping: (T) -> M): PageWrapper<M> {
        return PageWrapper(
            contents = contents.map(mapping),
            pageNo = pageNo,
            pageSize = pageSize,
            hasNext = hasNext,
        )
    }
}

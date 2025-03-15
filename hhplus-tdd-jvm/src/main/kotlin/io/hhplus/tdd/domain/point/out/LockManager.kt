package io.hhplus.tdd.domain.point.out

interface LockManager {
    fun <T> lockWith(
        id: Long,
        block: () -> T,
    ): T
}

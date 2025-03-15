package io.hhplus.tdd.fake

import io.hhplus.tdd.domain.point.out.LockManager

class FakeLockManger : LockManager {
    override fun <T> lockWith(
        id: Long,
        block: () -> T,
    ): T {
        // do nothing
        return block()
    }
}

package io.hhplus.tdd.adapter.point.lock

import io.hhplus.tdd.domain.point.out.LockManager
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock

@Component
class ReentrantLockManager : LockManager {
    companion object {
        val LOCK = ReentrantLock()
    }

    override fun <T> lockWith(
        id: Long,
        block: () -> T,
    ): T {
        if (!LOCK.tryLock(3L, TimeUnit.SECONDS)) {
            throw RuntimeException("Lock timeout")
        }
        try {
            return block()
        } finally {
            LOCK.unlock()
        }
    }
}

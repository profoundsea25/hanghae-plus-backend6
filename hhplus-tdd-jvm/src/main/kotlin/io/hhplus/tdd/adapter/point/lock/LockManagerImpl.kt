package io.hhplus.tdd.adapter.point.lock

import io.hhplus.tdd.domain.point.out.LockManager
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.locks.ReentrantLock

@Component
class LockManagerImpl : LockManager {
    companion object {
        val LOCK_MAP: ConcurrentMap<Long, ReentrantLock> = ConcurrentHashMap()
    }

    override fun <T> lockWith(
        id: Long,
        block: () -> T,
    ): T {
        val lock = LOCK_MAP.getOrPut(id) { ReentrantLock() }

        lock.lock()
        try {
            return block()
        } finally {
            lock.unlock()
        }
    }
}

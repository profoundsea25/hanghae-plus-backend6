package io.hhplus.tdd.adapter.point.api

import io.hhplus.tdd.application.point.PointService
import io.hhplus.tdd.domain.point.PointHistory
import io.hhplus.tdd.domain.point.UserPoint
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/point")
class PointController(
    private val pointService: PointService,
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("{id}")
    fun point(
        @PathVariable id: Long,
    ): UserPoint {
        return pointService.findUserPointBy(id)
    }

    @GetMapping("{id}/histories")
    fun history(
        @PathVariable id: Long,
    ): List<PointHistory> {
        return pointService.findAllPointHistoryBy(id)
    }

    @PatchMapping("{id}/charge")
    fun charge(
        @PathVariable id: Long,
        @RequestBody amount: Long,
    ): UserPoint {
        return pointService.charge(
            id = id,
            amount = amount,
        )
    }

    @PatchMapping("{id}/use")
    fun use(
        @PathVariable id: Long,
        @RequestBody amount: Long,
    ): UserPoint {
        return pointService.use(
            id = id,
            amount = amount,
        )
    }
}

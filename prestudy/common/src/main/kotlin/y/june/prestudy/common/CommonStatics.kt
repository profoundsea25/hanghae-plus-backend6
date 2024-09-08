package y.june.prestudy.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val DEFAULT_DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss"
val DEFAULT_DATE_TIME_FORMAT: DateTimeFormatter =
    DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT_PATTERN)

fun LocalDateTime.toDefaultFormat(): String {
    return this.format(DEFAULT_DATE_TIME_FORMAT)
}

package y.june.prestudy.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val defaultDateTimeFormatPattern = "yyyyMMdd HH:mm:ss"
val defaultDateTimeFormat: DateTimeFormatter =
    DateTimeFormatter.ofPattern(defaultDateTimeFormatPattern)

fun LocalDateTime.toDefaultFormat(): String {
    return this.format(defaultDateTimeFormat)
}

package com.sorabh.node.utils

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

enum class TaskDateRange(val value: String) {
    ALL("All"),
    TODAY("Today"),
    THIS_WEEK("This Week"),
    LAST_SEVEN_DAY("Last 7 days"),
    LAST_THIRTY_DAY("Last 30 days"),
    THIS_MONTH("This Month"),
    LAST_MONTH("Last Month"),
    CUSTOM_RANGE("Custom Range")
}

data class DateTimeRange(
    val start: LocalDateTime?,
    val end: LocalDateTime?
)


@OptIn(ExperimentalTime::class)
fun TaskDateRange.toDateTimeRange(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): DateTimeRange {

    val nowInstant = Clock.System.now()
    val today = nowInstant.toLocalDateTime(timeZone).date

    fun startOfDay(date: LocalDate): LocalDateTime =
        LocalDateTime(date, LocalTime(0, 0, 0, 0))

    fun endOfDay(date: LocalDate): LocalDateTime =
        LocalDateTime(date, LocalTime(23, 59, 59, 999_999_999))

    return when (this) {

        TaskDateRange.ALL -> DateTimeRange(
            start = null,
            end = null
        )

        TaskDateRange.TODAY -> DateTimeRange(
            start = startOfDay(today),
            end = endOfDay(today)
        )

        TaskDateRange.THIS_WEEK -> {
            val start = today.minus(DatePeriod(days = today.dayOfWeek.ordinal))
            DateTimeRange(
                start = startOfDay(start),
                end = endOfDay(today)
            )
        }

        TaskDateRange.LAST_SEVEN_DAY -> {
            val start = today.minus(DatePeriod(days = 6))
            DateTimeRange(
                start = startOfDay(start),
                end = endOfDay(today)
            )
        }

        TaskDateRange.LAST_THIRTY_DAY -> {
            val start = today.minus(DatePeriod(days = 29))
            DateTimeRange(
                start = startOfDay(start),
                end = endOfDay(today)
            )
        }

        TaskDateRange.THIS_MONTH -> {
            val start = LocalDate(today.year, today.month, 1)
            DateTimeRange(
                start = startOfDay(start),
                end = endOfDay(today)
            )
        }

        TaskDateRange.LAST_MONTH -> {
            val firstDayThisMonth = LocalDate(today.year, today.month, 1)
            val lastMonthLastDay = firstDayThisMonth.minus(DatePeriod(days = 1))
            val lastMonthFirstDay = LocalDate(
                lastMonthLastDay.year,
                lastMonthLastDay.month,
                1
            )

            DateTimeRange(
                start = startOfDay(lastMonthFirstDay),
                end = endOfDay(lastMonthLastDay)
            )
        }

        TaskDateRange.CUSTOM_RANGE -> DateTimeRange(null,null)
    }
}
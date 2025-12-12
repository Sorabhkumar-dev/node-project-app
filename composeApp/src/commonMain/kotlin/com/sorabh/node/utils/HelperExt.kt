package com.sorabh.node.utils

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun currentLocalDateTime(): LocalDateTime {
    val currentMoment: kotlin.time.Instant = kotlin.time.Clock.System.now()
    val datetimeInSystemZone: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())
    return datetimeInSystemZone
}

fun LocalDateTime.formatTaskDate() =
    format(LocalDateTime.Format {
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        char(' ')
        day()
        char(' ')
        char('•')
        char(' ')
        hour()
        char(':')
        minute()
    })

fun LocalDateTime.formatTaskDate2() =
    format(LocalDateTime.Format {
        hour()
        char(':')
        minute()
        char(' ')
        amPmMarker("AM","PM")
        char(' ')
        day()
        char(' ')
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        char(' ')
        year()
    })

val Color.main: Color get() = this.copy(0.7f)
val Color.container: Color get() = this.copy(0.1f)
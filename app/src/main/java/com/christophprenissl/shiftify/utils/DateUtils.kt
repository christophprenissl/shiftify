package com.christophprenissl.shiftify.utils

import java.util.*

fun Int.daysInTimeToMillis(): Long {
    return this * 24 * 60 * 60 * 1000.toLong()
}

fun Calendar.monthYearString(): String {
    return "${this.get(Calendar.MONTH)+1} ${this.get(Calendar.YEAR)}"
}

fun Calendar.isSameDayAs(otherCalendar: Calendar): Boolean {
    return this.get(Calendar.DAY_OF_YEAR) == otherCalendar.get(Calendar.DAY_OF_YEAR)
            && this.get(Calendar.YEAR) == otherCalendar.get(Calendar.YEAR)
}

fun Calendar.isInSameMonthAs(otherCalendar: Calendar): Boolean {
    return this.get(Calendar.MONTH) == otherCalendar.get(Calendar.MONTH)
}
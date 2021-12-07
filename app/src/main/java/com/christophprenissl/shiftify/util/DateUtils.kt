package com.christophprenissl.shiftify.util

import java.util.*

fun Int.daysInTimeToMillis(): Long {
    return this * 24 * 60 * 60 * 1000.toLong()
}

fun Calendar.monthYearString(): String {
    val monthName = when(this.get(Calendar.MONTH)) {
        Calendar.JANUARY -> "January"
        Calendar.FEBRUARY -> "February"
        Calendar.MARCH -> "March"
        Calendar.APRIL -> "April"
        Calendar.MAY -> "May"
        Calendar.JUNE -> "June"
        Calendar.JULY -> "July"
        Calendar.AUGUST -> "August"
        Calendar.SEPTEMBER -> "September"
        Calendar.OCTOBER -> "October"
        Calendar.NOVEMBER -> "November"
        Calendar.DECEMBER -> "December"
        else -> "Error"
    }

    return "$monthName  ${this.get(Calendar.YEAR)}"
}

fun Calendar.isSameDayAs(otherCalendar: Calendar): Boolean {
    return this.get(Calendar.DAY_OF_YEAR) == otherCalendar.get(Calendar.DAY_OF_YEAR)
            && this.get(Calendar.YEAR) == otherCalendar.get(Calendar.YEAR)
}

fun Calendar.isInSameMonthAs(otherCalendar: Calendar): Boolean {
    return this.get(Calendar.MONTH) == otherCalendar.get(Calendar.MONTH)
}
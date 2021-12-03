package com.christophprenissl.shiftify.utils

import java.util.*

fun Int.daysInTimeToMillis(): Long {
    return this * 24 * 60 * 60 * 1000.toLong()
}

fun Calendar.monthYearString(): String {
    return "${this.get(Calendar.MONTH)+1} ${this.get(Calendar.YEAR)}"
}
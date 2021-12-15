package com.christophprenissl.shiftify.util

const val WEEK_DAY_COUNT = 7

//Shift times
const val FREE_SHIFT_STARTING_MILLIS = 0L
const val FREE_SHIFT_ENDING_MILLIS = 24 * 3600000L
const val HOLIDAY_SHIFT_STARTING_MILLIS = 0L
const val HOLIDAY_SHIFT_ENDING_MILLIS = 24 * 3600000L
const val EARLY_SHIFT_STARTING_MILLIS = 6 * 3600000L + 45 * 60000L
const val EARLY_SHIFT_ENDING_MILLIS = 14 * 3600000L + 30 * 60000L
const val LATE_SHIFT_STARTING_MILLIS = 11 * 3600000L + 30 * 60000L
const val LATE_SHIFT_ENDING_MILLIS = 20 * 3600000L + 45 * 60000L
const val NIGHT_SHIFT_STARTING_MILLIS = 20 * 3600000L + 15 * 60000L
const val NIGHT_SHIFT_ENDING_MILLIS = (24 + 7) * 3600000L + 0 * 60000L

//Shift names
const val FREE_SHIFT_NAME = "Free Shift"
const val HOLIDAY_SHIFT_NAME = "Holiday Shift"
const val EARLY_SHIFT_NAME = "Early Shift"
const val LATE_SHIFT_NAME = "Late Shift"
const val NIGHT_SHIFT_NAME = "Night Shift"

//Priorities names
const val WISH_SHIFT_PRIORITY_NAME = "Wish Shift"
const val HIGH_PRIORITY_NAME = "High Priority"
const val NEUTRAL_PRIORITY_NAME = "Neutral Priority"
const val NEGATIVE_PRIORITY_NAME = "Negative Priority"

//Priorities
const val WISH_SHIFT_PRIORITY = 10
const val HIGH_PRIORITY = 3
const val NEUTRAL_PRIORITY = 2
const val NEGATIVE_PRIORITY = 1

//plan element types
const val FREE = 0
const val HOLIDAY = 1
const val EARLY_SHIFT = 2
const val LATE_SHIFT = 3
const val NIGHT_SHIFT = 4

//plan element approval states
const val PROCESSING = 0
const val HAS_CONFLICT = 1
const val APPROVED = 2
package com.christophprenissl.shiftify.model.dto

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class NurseDto(
    var lastName: String?,
    var firstName: String?,
    var isShiftOwner: Boolean?,
    var stationValue: String?,
    var holidaysPerYearCount: Int?,
    var hoursPerMonthCount: Int?
)

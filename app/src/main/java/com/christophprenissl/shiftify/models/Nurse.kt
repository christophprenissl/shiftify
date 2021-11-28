package com.christophprenissl.shiftify.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Nurse(
    var lastName: String?,
    var firstName: String?,
    var isShiftOwner: Boolean?,
    var stationValue: String?,
    var holidaysPerYearCount: Int?,
    var hoursPerMonthCount: Int?
) {

    @Exclude
    fun toMap() : Map<String, Any?> {
        return mapOf(
            "lastName" to lastName,
            "firstName" to firstName,
            "isShiftOwner" to isShiftOwner,
            "stationValue" to stationValue
        )
    }
}

package com.christophprenissl.shiftify.persistence.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Nurse(val id: String?,
                 val lastName: String?,
                 val firstName: String?,
                 val isShiftOwner: Boolean?,
                 val stationValue: String?,
                 val holidaysPerYearCount: Int?,
                 val hoursPerMonthCount: Int?
) {

    @Exclude
    fun toMap() : Map<String, Any?> {
        return mapOf(
            "id" to id,
            "lastName" to lastName,
            "firstName" to firstName,
            "isShiftOwner" to isShiftOwner,
            "stationValue" to stationValue
        )
    }
}

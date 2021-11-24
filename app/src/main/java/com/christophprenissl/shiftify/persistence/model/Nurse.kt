package com.christophprenissl.shiftify.persistence.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Nurse(val id: String?,
                 val name: String?,
                 val isShiftOwner: Boolean?,
                 val stationValue: String?) {

    @Exclude
    fun toMap() : Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to name,
            "isShiftOwner" to isShiftOwner,
            "stationValue" to stationValue
        )
    }
}

package com.christophprenissl.shiftify.model.dto

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class NurseDto(var lastName: String? = null,
                    var firstName: String? = null,
                    var isShiftOwner: Boolean? = null,
                    var stationValue: String? = null,
                    var holidaysPerYearCount: Int? = null,
                    var hoursPerMonthCount: Int? = null,
                    var nursePlanMonths: Map<String, NursePlanMonthDto>? = null)

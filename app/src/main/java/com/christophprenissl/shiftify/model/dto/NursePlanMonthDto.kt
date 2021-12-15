package com.christophprenissl.shiftify.model.dto

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class NursePlanMonthDto(var yearMonthName: String? = null,
                             var planElementList: List<PlanElementDto>? = null)


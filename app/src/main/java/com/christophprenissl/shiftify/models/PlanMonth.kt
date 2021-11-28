package com.christophprenissl.shiftify.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class PlanMonth(var yearMonth: String?,
                     var endDate: Long?,
                     var voteForBeginDate: Long?,
                     var voteForEndDate: Long?,
                     var minimalNurseCountEarly: Int?,
                     var minimalNurseCountLate: Int?,
                     var minimalNurseCountNight: Int?,
                     var isValid: Boolean = false,
                     var planElements: List<PlanElement>)


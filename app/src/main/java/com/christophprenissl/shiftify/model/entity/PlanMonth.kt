package com.christophprenissl.shiftify.model.entity

import java.util.*

data class PlanMonth(var yearMonth: String,
                     var endDate: Calendar,
                     var voteForBeginDate: Calendar,
                     var voteForEndDate: Calendar,
                     var minimalNurseCountEarly: Int,
                     var minimalNurseCountLate: Int,
                     var minimalNurseCountNight: Int,
                     var isValid: Boolean = false,
                     var planElementList: List<PlanElement>)
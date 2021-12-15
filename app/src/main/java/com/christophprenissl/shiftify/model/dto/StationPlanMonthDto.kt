package com.christophprenissl.shiftify.model.dto

data class StationPlanMonthDto(var yearMonthName: String? = null,
                               var voteBeginDate: Long? = null,
                               var voteEndDate: Long? = null,
                               var minimalNurseCountEarly: Int? = null,
                               var minimalNurseCountLate: Int? = null,
                               var minimalNurseCountNight: Int? = null,
                               var isValid: Boolean? = null,
                               var nursePlanElementList: Map<String, List<PlanElementDto>>? = null)

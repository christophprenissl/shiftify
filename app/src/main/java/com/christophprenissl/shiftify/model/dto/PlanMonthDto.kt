package com.christophprenissl.shiftify.model.dto

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class PlanMonthDto(var yearMonth: String?,
                        var endDate: Long?,
                        var voteForBeginDate: Long?,
                        var voteForEndDate: Long?,
                        var minimalNurseCountEarly: Int?,
                        var minimalNurseCountLate: Int?,
                        var minimalNurseCountNight: Int?,
                        var isValid: Boolean = false,
                        var planElementDtoList: List<PlanElementDto>?)


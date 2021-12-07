package com.christophprenissl.shiftify.model.entity

import java.util.*

data class PlanElement(var startDate: Calendar,
                       var endDate: Calendar,
                       var priority: Int,
                       var type: PlanElementType,
                       var approvalState: PlanElementApprovalState)

enum class PlanElementType {
    FREE,
    HOLIDAY,
    EARLY_SHIFT,
    LATE_SHIFT,
    NIGHT_SHIFT
}

enum class PlanElementApprovalState {
    PROCESSING,
    HAS_CONFLICT,
    APPROVED
}
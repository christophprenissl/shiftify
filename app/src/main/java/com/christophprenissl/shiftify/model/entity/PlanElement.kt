package com.christophprenissl.shiftify.model.entity

import java.util.*
import kotlin.collections.HashMap

data class PlanElement(var date: Calendar,
                       var priorityMap: HashMap<Shift, Int>,
                       var approvalState: PlanElementApprovalState)


enum class PlanElementApprovalState {
    PROCESSING,
    HAS_CONFLICT,
    APPROVED
}
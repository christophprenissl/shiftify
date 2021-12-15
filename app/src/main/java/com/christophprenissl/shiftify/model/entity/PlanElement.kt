package com.christophprenissl.shiftify.model.entity

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*
import kotlin.collections.HashMap

@IgnoreExtraProperties
data class PlanElement(var date: Calendar,
                       var priorityMap: HashMap<String, Int>,
                       var approvalState: PlanElementApprovalState) {

    fun getDeepCopy(): PlanElement {
        val dateCopy = date.clone() as Calendar
        val priorityMapCopy = HashMap<String,Int>(priorityMap)
        return  PlanElement(dateCopy, priorityMapCopy, approvalState)
    }
}


enum class PlanElementApprovalState {
    PROCESSING,
    HAS_CONFLICT,
    APPROVED
}
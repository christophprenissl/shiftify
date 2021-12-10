package com.christophprenissl.shiftify.model.dto

import com.christophprenissl.shiftify.util.PROCESSING
import com.google.firebase.database.IgnoreExtraProperties
import java.util.*
import kotlin.collections.HashMap

@IgnoreExtraProperties
data class PlanElementDto(var date: Long?,
                          var priorityMap: HashMap<ShiftDto, Int>?,
                            //approvalStates are defined in Constants
                          var approvalState: Int? = PROCESSING)

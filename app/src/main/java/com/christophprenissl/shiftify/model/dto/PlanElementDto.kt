package com.christophprenissl.shiftify.model.dto

import com.christophprenissl.shiftify.util.PROCESSING
import com.google.firebase.database.IgnoreExtraProperties
import java.util.*
import kotlin.collections.HashMap

@IgnoreExtraProperties
data class PlanElementDto(var dateName: String? = null,
                          var date: Long? = null,
                          var priorityMap: Map<String, Int>? = null,
                            //approvalStates are defined in Constants
                          var approvalState: String? = null)

package com.christophprenissl.shiftify.model.dto

import com.christophprenissl.shiftify.util.PROCESSING
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class PlanElementDto(var startDate: Long?,
                          var endDate: Long?,
                          var priority: Int?,
                        //types are defined in Constants.kt
                          var type: Int?,
                        //approvalStates are defined in Constants
                          var approvalState: Int? = PROCESSING)

package com.christophprenissl.shiftify.models

import com.christophprenissl.shiftify.utils.PROCESSING
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class PlanElement( var startDate: Long?,
                        var endDate: Long?,
                        var priority: Int?,
                        //types are defined in Constants.kt
                        var type: Int?,
                        //approvalStates are defined in Constants
                        var approvalState: Int? = PROCESSING)

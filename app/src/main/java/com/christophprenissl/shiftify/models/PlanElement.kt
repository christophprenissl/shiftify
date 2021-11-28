package com.christophprenissl.shiftify.models

import com.christophprenissl.shiftify.utils.PROCESSING
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class PlanElement( var startDate: Long?,
                        var endDate: Long?,
                        var priority: Int?,
                        var type: Int?,
                        var approvalState: Int? = PROCESSING)

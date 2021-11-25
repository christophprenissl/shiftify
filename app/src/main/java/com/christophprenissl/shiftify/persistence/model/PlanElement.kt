package com.christophprenissl.shiftify.persistence.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class PlanElement( val id : String?,
                        val startDate : Long?,
                        val endDate : Long?,
                        val priority : Int?,
                        val type : Int?,
                        val approvalState : Int?)

package com.christophprenissl.shiftify.persistence.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Poll(val id : String?,
                val endDate : Long?,
                val voteForBeginDate : Long?,
                val voteForEndDate : Long?,
                val minimalNurseCountEarly : Int?,
                val minimalNurseCountLate : Int?,
                val minimalNurseCountNight : Int?,
                val isApproved : Boolean = false)

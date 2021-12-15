package com.christophprenissl.shiftify.model.dto

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ShiftDto(var name: String? = null,
                    var startTimeMillis: Long? = null,
                    var endTimeMillis: Long? = null,)

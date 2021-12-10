package com.christophprenissl.shiftify.model.dto

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ShiftDto(var name: String?,
                    var startHours: Int?,
                    var startMinutes: Int?,
                    var durationInMinutes: Int?)

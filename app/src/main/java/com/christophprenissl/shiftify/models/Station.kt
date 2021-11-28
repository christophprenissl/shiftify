package com.christophprenissl.shiftify.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Station(var stationCode: String?,
                   var name: String?,
                   var lon: Double?,
                   var lat: Double?,
                   var planMonths: List<PlanMonth>?)

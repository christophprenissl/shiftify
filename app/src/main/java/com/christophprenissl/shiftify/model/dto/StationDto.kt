package com.christophprenissl.shiftify.model.dto

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class StationDto(var stationCode: String?,
                      var name: String?,
                      var lon: Double?,
                      var lat: Double?,
                      var planMonths: List<PlanMonthDto>?)

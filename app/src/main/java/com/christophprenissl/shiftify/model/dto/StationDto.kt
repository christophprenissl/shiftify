package com.christophprenissl.shiftify.model.dto

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class StationDto(var stationCode: String? = null,
                      var name: String? = null,
                      var lon: Double? = null,
                      var lat: Double? = null,
                      var nursePlanMonths: List<NursePlanMonthDto>? = null)

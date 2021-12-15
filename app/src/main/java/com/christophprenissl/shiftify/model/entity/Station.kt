package com.christophprenissl.shiftify.model.entity

import android.location.Location

data class Station(var stationCode: String,
                   var name: String,
                   var location: Location,
                   var nursePlanMonths: List<NursePlanMonth>)
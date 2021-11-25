package com.christophprenissl.shiftify.persistence.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Station(val stationCode : String?,
                   val name : String?,
                   val lon : Double?,
                   val lat : Double?,
                   val polls : List<String>?)

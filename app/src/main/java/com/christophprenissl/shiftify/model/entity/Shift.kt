package com.christophprenissl.shiftify.model.entity

data class Shift(var name: String,
                 var startHours: Int,
                 var startMinutes: Int,
                 var durationInMinutes: Int)

package com.christophprenissl.shiftify.model.entity

data class Nurse(var lastName: String,
                 var firstName: String,
                 var isShiftOwner: Boolean,
                 var stationValue: String,
                 var holidaysPerYearCount: Int,
                 var hoursPerMonthCount: Int,
                 var nursePlanMonths: HashMap<String, NursePlanMonth>)
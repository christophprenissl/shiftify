package com.christophprenissl.shiftify.model.entity

data class NursePlanMonth(var yearMonth: String,
                          var planElementList: List<PlanElement>)
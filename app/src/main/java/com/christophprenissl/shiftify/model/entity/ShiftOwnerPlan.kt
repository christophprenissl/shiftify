package com.christophprenissl.shiftify.model.entity

data class ShiftOwnerPlan(
    var planElementList: Map<String, Map<String, List<PlanElement>>>)

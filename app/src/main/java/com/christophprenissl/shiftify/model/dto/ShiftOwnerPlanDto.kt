package com.christophprenissl.shiftify.model.dto

data class ShiftOwnerPlanDto(
    var planElementList: Map<String, Map<String, PlanElementDto>>? = null)

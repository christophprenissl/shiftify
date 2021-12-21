package com.christophprenissl.shiftify.util.mapper

import com.christophprenissl.shiftify.model.dto.ShiftOwnerPlanDto
import com.christophprenissl.shiftify.model.entity.PlanElement
import com.christophprenissl.shiftify.model.entity.ShiftOwnerPlan

class ShiftOwnerPlanMapper: DataMapper<ShiftOwnerPlanDto, ShiftOwnerPlan> {
    private val planElementMapper = PlanElementMapper()

    override fun fromEntity(entity: ShiftOwnerPlan): ShiftOwnerPlanDto {

        return ShiftOwnerPlanDto(
            planElementList = entity.planElementMap.mapValues {
                it.value.mapValues { plan ->
                        planElementMapper.fromEntity(plan.value)
                }
            }
        )
    }

    override fun toEntity(domain: ShiftOwnerPlanDto): ShiftOwnerPlan {

        return ShiftOwnerPlan(
            planElementMap = (domain.planElementList?.mapValues {
                it.value.mapValues { element ->
                    planElementMapper.toEntity(element.value)
                }
            }) as Map<String, Map<String, PlanElement>>
        )
    }
}
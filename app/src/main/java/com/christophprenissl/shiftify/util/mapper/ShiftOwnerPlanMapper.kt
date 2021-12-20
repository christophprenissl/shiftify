package com.christophprenissl.shiftify.util.mapper

import com.christophprenissl.shiftify.model.dto.ShiftOwnerPlanDto
import com.christophprenissl.shiftify.model.entity.PlanElement
import com.christophprenissl.shiftify.model.entity.ShiftOwnerPlan

class ShiftOwnerPlanMapper: DataMapper<ShiftOwnerPlanDto, ShiftOwnerPlan> {
    private val planElementMapper = PlanElementMapper()

    override fun fromEntity(entity: ShiftOwnerPlan): ShiftOwnerPlanDto {

        return ShiftOwnerPlanDto(
            planElementList = entity.planElementList.mapValues {
                it.value.mapValues { planMap ->
                    planMap.value.map { element ->
                        planElementMapper.fromEntity(element)
                    }
                }
            }
        )
    }

    override fun toEntity(domain: ShiftOwnerPlanDto): ShiftOwnerPlan {

        return ShiftOwnerPlan(
            planElementList = (domain.planElementList?.mapValues {
                it.value.mapValues { mapElement ->
                    mapElement.value.map { element ->
                        planElementMapper.toEntity(element)
                    }
                }
            }) as Map<String, Map<String, List<PlanElement>>>
        )
    }
}
package com.christophprenissl.shiftify.util.mapper

import com.christophprenissl.shiftify.model.dto.NursePlanMonthDto
import com.christophprenissl.shiftify.model.entity.NursePlanMonth

class NursePlanMonthMapper: DataMapper<NursePlanMonthDto, NursePlanMonth> {
    private val planElementMapper = PlanElementMapper()

    override fun fromEntity(entity: NursePlanMonth): NursePlanMonthDto {

        return NursePlanMonthDto(
            yearMonthName = entity.yearMonth,
            planElementList = entity.planElementList.map { planElement ->
                    planElementMapper.fromEntity(planElement)
            }
        )
    }

    override fun toEntity(domain: NursePlanMonthDto): NursePlanMonth {

        return  NursePlanMonth(
            yearMonth = domain.yearMonthName?: "Error",
            planElementList = domain.planElementList?.map { planElementDto ->
                planElementMapper.toEntity(planElementDto)
            } ?: listOf()
        )
    }
}
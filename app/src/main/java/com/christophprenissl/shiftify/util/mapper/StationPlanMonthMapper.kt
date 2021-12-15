package com.christophprenissl.shiftify.util.mapper

import com.christophprenissl.shiftify.model.dto.PlanElementDto
import com.christophprenissl.shiftify.model.dto.StationPlanMonthDto
import com.christophprenissl.shiftify.model.entity.PlanElement
import com.christophprenissl.shiftify.model.entity.StationPlanMonth

class StationPlanMonthMapper: DataMapper<StationPlanMonthDto, StationPlanMonth> {
    private val planElementMapper = PlanElementMapper()

    override fun fromEntity(entity: StationPlanMonth): StationPlanMonthDto {

        val nursePlanElementList = mutableMapOf<String, List<PlanElementDto>>()
        entity.nursePlanElementList.forEach {
            nursePlanElementList[it.key] = it.value.map { planElement ->
                planElementMapper.fromEntity(planElement)
            }
        }

        return StationPlanMonthDto(
            yearMonthName = entity.yearMonthName,
            voteBeginDate = entity.voteBeginDate,
            voteEndDate = entity.voteEndDate,
            minimalNurseCountEarly = entity.minimalNurseCountEarly,
            minimalNurseCountLate = entity.minimalNurseCountLate,
            minimalNurseCountNight = entity.minimalNurseCountNight,
            isValid = entity.isValid,
            nursePlanElementList = nursePlanElementList)
    }

    override fun toEntity(domain: StationPlanMonthDto): StationPlanMonth {

        val nursePlanElementList = mutableMapOf<String, List<PlanElement>>()
        domain.nursePlanElementList?.forEach {
            nursePlanElementList[it.key] = it.value.map { planElement ->
                planElementMapper.toEntity(planElement)
            }
        }

        return  StationPlanMonth(
            yearMonthName = domain.yearMonthName?: "Error",
            voteBeginDate = domain.voteBeginDate?: -1,
            voteEndDate = domain.voteEndDate?: -1,
            minimalNurseCountEarly = domain.minimalNurseCountEarly?: -1,
            minimalNurseCountLate = domain.minimalNurseCountLate?: -1,
            minimalNurseCountNight = domain.minimalNurseCountNight?: -1,
            isValid = domain.isValid?: false,
            nursePlanElementList = nursePlanElementList
        )
    }
}
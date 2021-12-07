package com.christophprenissl.shiftify.util.mapper

import com.christophprenissl.shiftify.model.dto.PlanMonthDto
import com.christophprenissl.shiftify.model.entity.PlanMonth
import java.util.*

class PlanMonthMapper: DataMapper<PlanMonthDto, PlanMonth> {
    private val planElementMapper = PlanElementMapper()

    override fun fromEntity(entity: PlanMonth): PlanMonthDto {

        return PlanMonthDto(
            yearMonth = entity.yearMonth,
            endDate = entity.endDate.timeInMillis,
            voteForBeginDate = entity.voteForBeginDate.timeInMillis,
            voteForEndDate = entity.voteForEndDate.timeInMillis,
            minimalNurseCountEarly = entity.minimalNurseCountEarly,
            minimalNurseCountLate = entity.minimalNurseCountLate,
            minimalNurseCountNight = entity.minimalNurseCountNight,
            isValid = entity.isValid,
            planElementDtoList = entity.planElementList.map { planElement ->
                    planElementMapper.fromEntity(planElement)
            }
        )
    }

    override fun toEntity(domain: PlanMonthDto): PlanMonth {
        val endDate = Calendar.getInstance()
        endDate.timeInMillis = domain.endDate?: -1
        val voteForBeginDate = Calendar.getInstance()
        voteForBeginDate.timeInMillis = domain.voteForBeginDate?: -1
        val voteForEndDate = Calendar.getInstance()
        voteForEndDate.timeInMillis = domain.voteForEndDate?: -1

        return  PlanMonth(
            yearMonth = domain.yearMonth?: "Error",
            endDate = endDate,
            voteForBeginDate = voteForBeginDate,
            voteForEndDate = voteForEndDate,
            minimalNurseCountEarly = domain.minimalNurseCountEarly ?: 1,
            minimalNurseCountLate = domain.minimalNurseCountLate ?: 1,
            minimalNurseCountNight = domain.minimalNurseCountNight ?: 1,
            isValid = domain.isValid,
            planElementList = domain.planElementDtoList?.map { planElementDto ->
                planElementMapper.toEntity(planElementDto)
            } ?: listOf()
        )
    }
}
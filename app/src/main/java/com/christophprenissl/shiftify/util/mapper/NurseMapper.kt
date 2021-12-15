package com.christophprenissl.shiftify.util.mapper

import com.christophprenissl.shiftify.model.dto.NurseDto
import com.christophprenissl.shiftify.model.dto.NursePlanMonthDto
import com.christophprenissl.shiftify.model.entity.Nurse
import com.christophprenissl.shiftify.model.entity.NursePlanMonth

class NurseMapper: DataMapper<NurseDto, Nurse>{
    private val planMonthMapper = NursePlanMonthMapper()

    override fun fromEntity(entity: Nurse): NurseDto {
        val planMonthsDto = hashMapOf<String, NursePlanMonthDto>()
        for (planMonth in entity.nursePlanMonths) {
            planMonthsDto[planMonth.key] = planMonthMapper.fromEntity(planMonth.value)
        }
        return NurseDto(
            lastName = entity.lastName,
            firstName = entity.firstName,
            isShiftOwner = entity.isShiftOwner,
            stationValue = entity.stationValue,
            holidaysPerYearCount = entity.holidaysPerYearCount,
            hoursPerMonthCount = entity.hoursPerMonthCount,
            nursePlanMonths = planMonthsDto
        )
    }

    override fun toEntity(domain: NurseDto): Nurse {
        val planMonths = hashMapOf<String, NursePlanMonth>()
        domain.nursePlanMonths?.forEach {
            planMonths[it.key] = planMonthMapper.toEntity(it.value)
        }

        return Nurse(
            lastName = domain.lastName ?: "Mastermind",
            firstName = domain.firstName ?: "Jack",
            isShiftOwner = domain.isShiftOwner ?: false,
            stationValue = domain.stationValue ?: "null",
            holidaysPerYearCount = domain.holidaysPerYearCount ?: 0,
            hoursPerMonthCount = domain.hoursPerMonthCount ?: 0,
            nursePlanMonths = planMonths
        )
    }
}
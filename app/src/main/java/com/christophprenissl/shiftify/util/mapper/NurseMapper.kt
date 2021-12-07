package com.christophprenissl.shiftify.util.mapper

import com.christophprenissl.shiftify.model.dto.NurseDto
import com.christophprenissl.shiftify.model.entity.Nurse

class NurseMapper: DataMapper<NurseDto, Nurse>{
    override fun fromEntity(entity: Nurse): NurseDto {
        return NurseDto(
            lastName = entity.lastName,
            firstName = entity.firstName,
            isShiftOwner = entity.isShiftOwner,
            stationValue = entity.stationValue,
            holidaysPerYearCount = entity.holidaysPerYearCount,
            hoursPerMonthCount = entity.hoursPerMonthCount
        )
    }

    override fun toEntity(domain: NurseDto): Nurse {
        return Nurse(
            lastName = domain.lastName ?: "Mastermind",
            firstName = domain.firstName ?: "Jack",
            isShiftOwner = domain.isShiftOwner ?: false,
            stationValue = domain.stationValue ?: "null",
            holidaysPerYearCount = domain.holidaysPerYearCount ?: 0,
            hoursPerMonthCount = domain.hoursPerMonthCount ?: 0
        )
    }
}
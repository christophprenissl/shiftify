package com.christophprenissl.shiftify.util.mapper

import com.christophprenissl.shiftify.model.dto.ShiftDto
import com.christophprenissl.shiftify.model.entity.Shift

class ShiftMapper: DataMapper<ShiftDto, Shift> {
    override fun fromEntity(entity: Shift): ShiftDto {
        return ShiftDto(
            name = entity.name,
            startHours = entity.startHours,
            startMinutes = entity.startMinutes,
            durationInMinutes = entity.durationInMinutes
        )
    }

    override fun toEntity(domain: ShiftDto): Shift {
        return Shift(
            name = domain.name.toString(),
            startHours = domain.startHours?: -1,
            startMinutes = domain.startMinutes?: -1,
            durationInMinutes = domain.durationInMinutes?: -1
        )
    }
}
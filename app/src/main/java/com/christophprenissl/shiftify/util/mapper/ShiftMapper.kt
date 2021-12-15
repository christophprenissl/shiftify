package com.christophprenissl.shiftify.util.mapper

import com.christophprenissl.shiftify.model.dto.ShiftDto
import com.christophprenissl.shiftify.model.entity.Shift

class ShiftMapper: DataMapper<ShiftDto, Shift> {
    override fun fromEntity(entity: Shift): ShiftDto {
        return ShiftDto(
            name = entity.name,
            startTimeMillis = entity.startTimeMillis,
            endTimeMillis = entity.endTimeMillis
        )
    }

    override fun toEntity(domain: ShiftDto): Shift {
        return Shift(
            name = domain.name.toString(),
            startTimeMillis = domain.startTimeMillis?: -1,
            endTimeMillis = domain.endTimeMillis?: -1
        )
    }
}
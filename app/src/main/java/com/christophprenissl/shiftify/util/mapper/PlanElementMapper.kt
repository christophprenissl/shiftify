package com.christophprenissl.shiftify.util.mapper

import com.christophprenissl.shiftify.model.dto.PlanElementDto
import com.christophprenissl.shiftify.model.entity.PlanElement
import com.christophprenissl.shiftify.model.entity.PlanElementApprovalState
import com.christophprenissl.shiftify.model.entity.PlanElementType
import java.util.*

class PlanElementMapper: DataMapper<PlanElementDto, PlanElement> {
    override fun fromEntity(entity: PlanElement): PlanElementDto {
        return PlanElementDto(
            startDate = entity.startDate.timeInMillis,
            endDate = entity.endDate.timeInMillis,
            priority = entity.priority,
            type = entity.type.ordinal,
            approvalState = entity.approvalState.ordinal
            )
    }

    override fun toEntity(domain: PlanElementDto): PlanElement {
        val startDate = Calendar.getInstance()
        startDate.timeInMillis = domain.startDate?: -1
        val endDate = Calendar.getInstance()
        endDate.timeInMillis = domain.endDate?: -1

        return PlanElement(
            startDate = startDate,
            endDate = endDate,
            priority = domain.priority?: 0,
            type = PlanElementType.values()[domain.type?:0],
            approvalState = PlanElementApprovalState.values()[domain.approvalState?:0]
        )
    }
}
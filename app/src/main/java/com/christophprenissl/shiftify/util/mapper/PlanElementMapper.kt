package com.christophprenissl.shiftify.util.mapper

import com.christophprenissl.shiftify.model.dto.PlanElementDto
import com.christophprenissl.shiftify.model.entity.PlanElement
import com.christophprenissl.shiftify.model.entity.PlanElementApprovalState
import com.christophprenissl.shiftify.util.dayMonthYearString
import java.util.*

class PlanElementMapper: DataMapper<PlanElementDto, PlanElement> {

    private val shiftMapper = ShiftMapper()

    override fun fromEntity(entity: PlanElement): PlanElementDto {
        val priorityMapDto = hashMapOf<String, Int>()
        entity.priorityMap.forEach {
            priorityMapDto[it.key] = it.value
        }

        return PlanElementDto(
            dateName = entity.date.dayMonthYearString(),
            date = entity.date.timeInMillis,
            priorityMap = priorityMapDto,
            approvalState = entity.approvalState.name)
    }

    override fun toEntity(domain: PlanElementDto): PlanElement {
        val date = Calendar.getInstance()
        date.timeInMillis = domain.date?: -1

        val priorityMap = hashMapOf<String, Int>()
        domain.priorityMap?.forEach {
            val key = it.key
            priorityMap[key] = it.value
        }

        return PlanElement(
            date = date,
            priorityMap = priorityMap,
            approvalState = domain.approvalState?.let { PlanElementApprovalState.valueOf(it) } ?: PlanElementApprovalState.PROCESSING
        )
    }
}
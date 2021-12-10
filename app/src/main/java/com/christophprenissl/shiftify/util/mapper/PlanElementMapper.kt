package com.christophprenissl.shiftify.util.mapper

import com.christophprenissl.shiftify.model.dto.PlanElementDto
import com.christophprenissl.shiftify.model.dto.ShiftDto
import com.christophprenissl.shiftify.model.entity.PlanElement
import com.christophprenissl.shiftify.model.entity.PlanElementApprovalState
import com.christophprenissl.shiftify.model.entity.Shift
import java.util.*
import kotlin.collections.HashMap

class PlanElementMapper: DataMapper<PlanElementDto, PlanElement> {

    private val shiftMapper = ShiftMapper()

    override fun fromEntity(entity: PlanElement): PlanElementDto {
        val priorityMapDto = hashMapOf<ShiftDto, Int>()
        entity.priorityMap.forEach {
            val key = shiftMapper.fromEntity(it.key)
            priorityMapDto[key] = it.value
        }

        return PlanElementDto(
            date = entity.date.timeInMillis,
            priorityMap = priorityMapDto,
            approvalState = entity.approvalState.ordinal)
    }

    override fun toEntity(domain: PlanElementDto): PlanElement {
        val date = Calendar.getInstance()
        date.timeInMillis = domain.date?: -1

        val priorityMap = hashMapOf<Shift, Int>()
        domain.priorityMap?.forEach {
            val key = shiftMapper.toEntity(it.key)
            priorityMap[key] = it.value
        }

        return PlanElement(
            date = date,
            priorityMap = priorityMap,
            approvalState = PlanElementApprovalState.values()[domain.approvalState?:0]
        )
    }
}
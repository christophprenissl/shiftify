package com.christophprenissl.shiftify.util.mapper

import com.christophprenissl.shiftify.model.dto.StationDto
import com.christophprenissl.shiftify.model.entity.Station
import android.location.Location

class StationMapper: DataMapper<StationDto, Station> {

    private val planMonthMapper = PlanMonthMapper()

    override fun fromEntity(entity: Station): StationDto {
        return StationDto(
            stationCode = entity.stationCode,
            name = entity.name,
            lon = entity.location.longitude,
            lat = entity.location.latitude,
            planMonths = entity.planMonths.map { planMonth ->
                planMonthMapper.fromEntity(planMonth)
            }
        )
    }

    override fun toEntity(domain: StationDto): Station {
        val location = Location("dummy provider")
        location.latitude = domain.lat?: 0.0
        location.longitude = domain.lon?: 0.0

        return Station(
            stationCode = domain.stationCode?: "ERROR",
            name = domain.name?: "ERROR",
            location = location,
            planMonths = domain.planMonths?.map { planMonthDto ->
                planMonthMapper.toEntity(planMonthDto)
            } ?: listOf()
        )
    }
}
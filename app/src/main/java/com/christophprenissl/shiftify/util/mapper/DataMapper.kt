package com.christophprenissl.shiftify.util.mapper

interface DataMapper<D, E> {

    fun fromEntity(entity: E): D

    fun toEntity(domain: D): E
}
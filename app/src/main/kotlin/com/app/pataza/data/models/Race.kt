package com.app.pataza.data.models

import com.app.pataza.data.resource.ResourceEntity

data class Race(val value: Int, val label: String){
    fun toRaceEntity() = ResourceEntity.RaceEntity(value, label)
}
package com.app.pataza.data.models

import com.app.pataza.data.resource.ResourceEntity

data class Vaccine(val value: Int, val label: String){
    fun toVaccineEntity() = ResourceEntity.VaccineEntity(value, label)
}
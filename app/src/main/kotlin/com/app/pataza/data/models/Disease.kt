package com.app.pataza.data.models

import com.app.pataza.data.resource.ResourceEntity

data class Disease(val value: Int, val label: String){
    fun toDiseaseEntity() = ResourceEntity.DiseaseEntity(value, label)
}
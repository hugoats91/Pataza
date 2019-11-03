package com.app.pataza.data.models

import com.app.pataza.data.resource.ResourceEntity

data class Quality(val value: Int, val label: String){
    fun toQualityEntity() = ResourceEntity.QualityEntity(value, label)
}
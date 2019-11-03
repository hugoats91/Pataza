package com.app.pataza.data.models

import com.app.pataza.data.resource.ResourceEntity

data class Size(val value: Int, val label: String){
    fun toSizeEntity() = ResourceEntity.SizeEntity(value, label)
}
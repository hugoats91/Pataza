package com.app.pataza.data.models

import com.app.pataza.data.resource.ResourceEntity

data class Country(val value: String, val label: String, val code: String){
    fun toCountryEntity() = ResourceEntity.CountryEntity(value = value, label = label, code = code)
}
package com.app.pataza.data.models

import com.app.pataza.features.pets.PetView
import com.google.gson.annotations.SerializedName

data class Pet(@SerializedName("_id") val petId: String, val enabled: Boolean, val name: String, val birthDate: String?, val color: List<String>, val size: Double, val weight: Double, val description: String, val photos: List<Photo>){
    fun toPetView() = PetView(petId, enabled, name, birthDate, color, size, weight, description, photos.map { it.toPhotoView() })
}
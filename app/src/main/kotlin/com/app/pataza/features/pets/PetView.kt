package com.app.pataza.features.pets

data class PetView(val petId: String, val enabled: Boolean, val name: String, val birthDate: String?, val color: List<String>, val size: Double, val weight: Double, val description: String, val photos: List<PhotoView>)
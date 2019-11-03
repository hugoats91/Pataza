package com.app.pataza.features.pets.add

import com.google.gson.annotations.SerializedName

class AddPetRemote{
    data class Request(val name: String,
                       val monthsAge: Int?,
                       val yearsAge: Int?,
                       val race: Int,
                       val color: List<String>,
                       val gender: Int,
                       val size: Int,
                       val weight: Double?,
                       val description: String,
                       val vaccines: List<Int>?,
                       val qualities: List<Int>?,
                       val diseases: List<Int>?,
                       val additionalRequirements: List<Int>?)

    data class Response(@SerializedName("pet") val pet: Pet)

    data class Pet(@SerializedName("_id") val id: String, val enabled: Boolean, val owner: String, val name: String, val color: List<String>, val gender: Int, val size: Int, val weight: Double?, val description: String)
}
package com.app.pataza.features.pets.add

class Pet{
    data class Request(val name: String, val birthdate: String, val color: String, val size: Double, val weight: Double, val description: String)

    data class Response(val _id: String, val enabled: Boolean, val name: String, val birthdate: String, val color: String, val size: Double, val weight: Double, val description: String)
}
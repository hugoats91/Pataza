package com.app.pataza.features.profile

class Profile {
    data class Request(val name: String, val phone: String, val birthdate: String, val address: String, val country: String, val latitude: Double, val longitude: Double)
    data class Response(var name: String, var email: String, var phone: String, var birthdate: String, var latitude: Double, var longitude: Double, var address: String?, var country: String?, var pets: Int)
}
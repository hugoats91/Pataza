package com.app.pataza.data.models

import com.app.pataza.data.user.UserEntity

data class User(var name: String, var email: String, var phone: String, var birthdate: String, var latitude: Double, var longitude: Double, var address: String?, var country: String?, var pets: Int){
    fun toUserEntity() = UserEntity(name = name, email = email, phone = phone, birthdate = birthdate, latitude = latitude, longitude = longitude, address = address?:"", country = country?:"", pets = pets)
}
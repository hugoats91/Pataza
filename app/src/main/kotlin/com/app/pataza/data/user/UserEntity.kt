package com.app.pataza.data.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.pataza.data.models.Country
import com.app.pataza.data.models.User

@Entity(tableName = "user_table")
data class UserEntity(
        @PrimaryKey(autoGenerate = true) var id: Int = 0,
        var name: String,
        var email: String,
        var phone: String,
        var birthdate: String,
        var latitude: Double,
        var longitude: Double,
        var address: String,
        var country: String,
        var pets: Int
){
    fun toUser() = User(name, email, phone, birthdate, latitude, longitude, address, country, pets)
}
package com.app.pataza.data.user

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.app.pataza.features.login.User

@Entity(tableName = "user_table")
data class UserEntity(
        @PrimaryKey(autoGenerate = true) var id: Int = 0,
        var name: String
){
    fun toUser() = User(name)
}
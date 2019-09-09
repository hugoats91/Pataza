package com.app.pataza.data.user

import androidx.room.Dao
import androidx.room.Query

@Dao
interface UserDao {
    @Query("select * from user_table")
    fun getUser(): MutableList<UserEntity>
}
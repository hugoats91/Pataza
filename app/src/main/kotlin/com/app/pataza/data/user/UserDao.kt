package com.app.pataza.data.user

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query

@Dao
interface UserDao {
    @Query("select * from user_table")
    fun getUser(): MutableList<UserEntity>
}
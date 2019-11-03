package com.app.pataza.data.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.pataza.data.resource.ResourceEntity

@Dao
interface UserDao {
    @Query("select * from user_table")
    fun getUser(): MutableList<UserEntity>

    @Insert(onConflict = 1)
    fun insert(user: UserEntity)
}
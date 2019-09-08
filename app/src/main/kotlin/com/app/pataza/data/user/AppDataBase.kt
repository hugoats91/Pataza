package com.app.pataza.data.user

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDataBase: RoomDatabase(){
    abstract fun userDao(): UserDao
}
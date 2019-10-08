package com.app.pataza.data.user

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.pataza.data.resource.ResourceDao
import com.app.pataza.data.resource.ResourceEntity

@Database(entities = [UserEntity::class, ResourceEntity.CountryEntity::class, ResourceEntity.RaceEntity::class], version = 1)
abstract class AppDataBase: RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun resourceDao(): ResourceDao
}
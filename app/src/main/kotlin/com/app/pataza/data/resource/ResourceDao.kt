package com.app.pataza.data.resource

import androidx.room.Dao
import androidx.room.Insert


@Dao
interface ResourceDao {
    @Insert(onConflict = 1)
    fun insertAll(pets: List<ResourceEntity.CountryEntity>)
}
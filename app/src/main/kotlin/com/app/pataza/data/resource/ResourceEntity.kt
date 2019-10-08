package com.app.pataza.data.resource

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.pataza.data.models.Country
import com.app.pataza.data.models.Race


class ResourceEntity {
    @Entity(tableName = "country_table")
    data class CountryEntity(
            @PrimaryKey(autoGenerate = true) var id: Int = 0,
            var value: String,
            var label: String,
            var code: String
    ) {
        fun toCountry() = Country(value, label, code)
    }

    @Entity(tableName = "race_table")
    data class RaceEntity(
            @PrimaryKey(autoGenerate = false) var value: Int = 0,
            var label: String
    ) {
        fun toRace() = Race(value, label)
    }
}
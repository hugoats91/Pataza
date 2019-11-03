package com.app.pataza.data.resource

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.pataza.data.models.*


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

    @Entity(tableName = "size_table")
    data class SizeEntity(
            @PrimaryKey(autoGenerate = false) var value: Int = 0,
            var label: String
    ) {
        fun toSize() = Size(value, label)
    }

    @Entity(tableName = "disease_table")
    data class DiseaseEntity(
            @PrimaryKey(autoGenerate = false) var value: Int = 0,
            var label: String
    ) {
        fun toDisease() = Disease(value, label)
    }

    @Entity(tableName = "veccine_table")
    data class VaccineEntity(
            @PrimaryKey(autoGenerate = false) var value: Int = 0,
            var label: String
    ) {
        fun toVaccine() = Vaccine(value, label)
    }

    @Entity(tableName = "quality_table")
    data class QualityEntity(
            @PrimaryKey(autoGenerate = false) var value: Int = 0,
            var label: String
    ) {
        fun toQuality() = Quality(value, label)
    }
}
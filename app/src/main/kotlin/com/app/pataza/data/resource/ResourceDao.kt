package com.app.pataza.data.resource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.pataza.data.user.UserEntity


@Dao
interface ResourceDao {
    @Insert(onConflict = 1)
    fun insertAllPets(pets: List<ResourceEntity.CountryEntity>)
    @Insert(onConflict = 1)
    fun insertAllRaces(races: List<ResourceEntity.RaceEntity>)
    @Insert(onConflict = 1)
    fun insertAllSizes(sizes: List<ResourceEntity.SizeEntity>)
    @Insert(onConflict = 1)
    fun insertAllDiseases(diseases: List<ResourceEntity.DiseaseEntity>)
    @Insert(onConflict = 1)
    fun insertAllVaccines(vaccines: List<ResourceEntity.VaccineEntity>)
    @Insert(onConflict = 1)
    fun insertAllQualities(qualities: List<ResourceEntity.QualityEntity>)
    @Query("select * from country_table")
    fun getCountries(): MutableList<ResourceEntity.CountryEntity>
    @Query("select * from race_table")
    fun getRaces(): MutableList<ResourceEntity.RaceEntity>
    @Query("select * from size_table")
    fun getSizes(): MutableList<ResourceEntity.SizeEntity>
    @Query("select * from disease_table")
    fun getDiseases(): MutableList<ResourceEntity.DiseaseEntity>
    @Query("select * from veccine_table")
    fun getVaccines(): MutableList<ResourceEntity.VaccineEntity>
    @Query("select * from quality_table")
    fun getQualities(): MutableList<ResourceEntity.QualityEntity>
    @Query("select * from country_table where value =:param")
    fun getCountryById(param: String): MutableList<ResourceEntity.CountryEntity>
}
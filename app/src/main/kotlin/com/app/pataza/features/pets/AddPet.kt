package com.app.pataza.features.pets

import com.app.pataza.core.interactor.UseCase
import javax.inject.Inject

class AddPet
@Inject constructor(private val petRepository: PetRepository) : UseCase<String, AddPet.Params>() {

    override suspend fun run(params: Params) = petRepository.addPet(params.name,
            params.monthsAge,
            params.yearsAge,
            params.race,
            params.color,
            params.gender,
            params.size,
            params.weight,
            params.description,
            params.vaccines,
            params.qualities,
            params.diseases,
            params.additionalRequirements)

    data class Params(val name: String,
                      val monthsAge: Int?,
                      val yearsAge: Int?,
                      val race: Int,
                      val color: List<String>,
                      val gender: Int,
                      val size: Int,
                      val weight: Double?,
                      val description: String,
                      val vaccines: List<Int>?,
                      val qualities: List<Int>?,
                      val diseases: List<Int>?,
                      val additionalRequirements: List<Int>?)
}
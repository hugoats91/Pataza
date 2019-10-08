package com.app.pataza.features.pets

import com.app.pataza.core.interactor.UseCase
import javax.inject.Inject

class AddPet
@Inject constructor(private val petRepository: PetRepository) : UseCase<Boolean, AddPet.Params>() {

    override suspend fun run(params: Params) = petRepository.addPet(params.name, params.birthdate, params.color, params.size, params.weight, params.description)

    data class Params(val name: String, val birthdate: String, val color: String, val size: Double, val weight: Double, val description: String)
}
package com.app.pataza.features.profile.pets

import com.app.pataza.core.interactor.UseCase
import com.app.pataza.data.models.Pet
import com.app.pataza.features.pets.PetRepository
import javax.inject.Inject

class PetsByUser
@Inject constructor(private val petRepository: PetRepository) : UseCase<List<Pet>, UseCase.None>() {

    override suspend fun run(params: None) = petRepository.petsByUser()
}
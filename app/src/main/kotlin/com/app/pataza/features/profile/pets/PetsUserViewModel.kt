package com.app.pataza.features.profile.pets

import androidx.lifecycle.MutableLiveData
import com.app.pataza.core.interactor.UseCase
import com.app.pataza.core.platform.BaseViewModel
import com.app.pataza.data.models.Pet
import com.app.pataza.features.pets.PetView
import javax.inject.Inject

class PetsUserViewModel
@Inject constructor(private val petsByUser: PetsByUser) : BaseViewModel() {

    var pets: MutableLiveData<List<PetView>> = MutableLiveData()

    fun petsByUser() = petsByUser(UseCase.None()) { it.either(::handleFailure, ::handlePets) }

    private fun handlePets(list: List<Pet>) {
        this.pets.value = list.map { it.toPetView() }
    }
}
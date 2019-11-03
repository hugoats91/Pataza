package com.app.pataza.features.pets.add

import androidx.lifecycle.MutableLiveData
import com.app.pataza.core.interactor.UseCase
import com.app.pataza.core.platform.BaseViewModel
import com.app.pataza.data.models.Resource
import com.app.pataza.features.pets.AddPet
import com.app.pataza.features.pets.SendPhotos
import okhttp3.MultipartBody
import javax.inject.Inject

class AddPetViewModel
@Inject constructor(private val getResources: GetResources, private val sendPhotos: SendPhotos, private val addPet: AddPet) : BaseViewModel() {

    var resources: MutableLiveData<Resource> = MutableLiveData()

    var successPhotos: MutableLiveData<Boolean> = MutableLiveData()
    var successId: MutableLiveData<String> = MutableLiveData()

    fun allResources() = getResources(UseCase.None()) { it.either(::handleFailure, ::handleResource) }

    private fun handleResource(resources: Resource) {
        this.resources.value = resources
    }

    fun sendPhotos(petId: String, file: List<MultipartBody.Part>) = sendPhotos(SendPhotos.Params(petId, file)) { it.either(::handleFailure, ::handlePhoto) }

    private fun handlePhoto(success: Boolean) {
        this.successPhotos.value = success
    }

    fun addPet(name: String,
               monthsAge: Int?,
               yearsAge: Int?,
               race: Int,
               color: List<String>,
               gender: Int,
               size: Int,
               weight: Double?,
               description: String,
               vaccines: List<Int>?,
               qualities: List<Int>?,
               diseases: List<Int>?,
               additionalRequirements: List<Int>?) = addPet(AddPet.Params(name, monthsAge, yearsAge, race, color, gender, size, weight, description, vaccines, qualities, diseases, additionalRequirements)){ it.either(::handleFailure, ::handlePet)}

    private fun handlePet(id: String) {
        this.successId.value = id
    }
}
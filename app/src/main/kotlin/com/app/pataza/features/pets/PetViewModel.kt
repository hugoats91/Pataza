package com.app.pataza.features.pets

import androidx.lifecycle.MutableLiveData
import com.app.pataza.core.platform.BaseViewModel
import okhttp3.MultipartBody
import javax.inject.Inject

class PetViewModel
@Inject constructor(private val sendPhotos: SendPhotos, private val addPet: AddPet) : BaseViewModel() {

    var successPhotos: MutableLiveData<Boolean> = MutableLiveData()
    var successPet: MutableLiveData<Boolean> = MutableLiveData()

    fun sendPhotos(petId: String, file: List<MultipartBody.Part>) = sendPhotos(SendPhotos.Params(petId, file)) { it.either(::handleFailure, ::handlePhoto) }

    private fun handlePhoto(success: Boolean) {
        this.successPhotos.value = success
    }

    fun addPet(name: String, birthdate: String, color: String, size: Double, weight: Double, description: String) = addPet(AddPet.Params(name, birthdate, color, size, weight, description)){ it.either(::handleFailure, ::handlePet)}

    private fun handlePet(success: Boolean) {
        this.successPet.value = success
    }
}
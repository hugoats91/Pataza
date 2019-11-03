package com.app.pataza.features.pets

import androidx.lifecycle.MutableLiveData
import com.app.pataza.core.interactor.UseCase
import com.app.pataza.core.platform.BaseViewModel
import com.app.pataza.features.profile.pets.PetsByUser
import okhttp3.MultipartBody
import javax.inject.Inject

class PetViewModel
@Inject constructor(private val sendPhotos: SendPhotos, private val addPet: AddPet, private val petsByUser: PetsByUser) : BaseViewModel() {

    var successPhotos: MutableLiveData<Boolean> = MutableLiveData()
    var successId: MutableLiveData<String> = MutableLiveData()

    fun sendPhotos(petId: String, file: List<MultipartBody.Part>) = sendPhotos(SendPhotos.Params(petId, file)) { it.either(::handleFailure, ::handlePhoto) }

    private fun handlePhoto(success: Boolean) {
        this.successPhotos.value = success
    }
}
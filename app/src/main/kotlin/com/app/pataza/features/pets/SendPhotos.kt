package com.app.pataza.features.pets

import com.app.pataza.core.interactor.UseCase
import okhttp3.MultipartBody
import javax.inject.Inject

class SendPhotos
@Inject constructor(private val petRepository: PetRepository) : UseCase<Boolean, SendPhotos.Params>() {

    override suspend fun run(params: Params) = petRepository.sendPetPhotos(params.petId, params.file)

    data class Params(val petId: String, val file: List<MultipartBody.Part>)
}
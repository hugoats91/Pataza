package com.app.pataza.features.pets

import com.app.pataza.features.pets.add.AddPetRemote
import okhttp3.MultipartBody
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PetService
@Inject constructor(retrofit: Retrofit) : PetApi {
    private val petApi by lazy { retrofit.create(PetApi::class.java) }

    override fun uploadPetPhotos(petId: String, file: List<MultipartBody.Part>) = petApi.uploadPetPhotos(petId, file)

    override fun addPet(request: AddPetRemote.Request) = petApi.addPet(request)

    override fun petsByUser() = petApi.petsByUser()
}

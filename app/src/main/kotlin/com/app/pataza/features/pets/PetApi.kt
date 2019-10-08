package com.app.pataza.features.pets

import com.app.pataza.core.functional.BaseResponse
import com.app.pataza.features.pets.add.Pet
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

internal interface PetApi {

    @Multipart
    @POST("user/pet/{petId}/photos")
    fun uploadPetPhotos(@Path("petId") petId: String, @Part file: List<MultipartBody.Part>): Call<BaseResponse<Void>>

    @POST("user/pet")
    fun addPet(@Body request: Pet.Request): Call<BaseResponse<Pet.Response>>
}

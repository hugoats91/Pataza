package com.app.pataza.features.pets

import com.app.pataza.core.functional.BaseResponse
import com.app.pataza.data.models.Pet
import com.app.pataza.features.pets.add.AddPetRemote
import com.app.pataza.features.profile.pets.MyPetsRemote
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

internal interface PetApi {

    @Multipart
    @POST("user/pet/{petId}/photos")
    fun uploadPetPhotos(@Path("petId") petId: String, @Part file: List<MultipartBody.Part>): Call<BaseResponse<Void>>

    @POST("user/pet")
    fun addPet(@Body request: AddPetRemote.Request): Call<BaseResponse<AddPetRemote.Response>>

    @GET("user/pet")
    fun petsByUser(): Call<BaseResponse<MyPetsRemote.Response>>
}

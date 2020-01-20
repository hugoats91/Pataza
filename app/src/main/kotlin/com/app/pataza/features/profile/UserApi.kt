package com.app.pataza.features.profile

import com.app.pataza.core.functional.BaseResponse
import com.app.pataza.data.models.Resource
import com.app.pataza.data.models.User
import com.app.pataza.features.profile.login.Login
import com.app.pataza.features.profile.register.Register
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

internal interface UserApi {

    @POST("user/login")
    fun login(@Body request: Login.Request): Call<BaseResponse<Login.Response>>

    @POST("user/logout")
    fun logout(): Call<BaseResponse<Boolean>>

    @GET("app/initialResources")
    fun appResources(): Call<BaseResponse<Resource>>

    @GET("user/profile")
    fun getProfile(): Call<BaseResponse<User>>

    @PUT("user/profile")
    fun editProfile(@Body request: Profile.Request): Call<BaseResponse<Void>>

    @POST("user/signup")
    fun doRegister(@Body request: Register.Request): Call<BaseResponse<Register.Response>>
}

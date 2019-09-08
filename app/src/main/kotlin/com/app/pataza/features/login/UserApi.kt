package com.app.pataza.features.login

import com.app.pataza.core.functional.BaseResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

internal interface UserApi {

    @POST("user/login")
    fun login(@Body request: Login.Request): Call<BaseResponse<Login.Response>>
}

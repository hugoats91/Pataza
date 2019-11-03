package com.app.pataza.features.profile

import com.app.pataza.core.functional.BaseResponse
import com.app.pataza.data.models.User
import com.app.pataza.features.profile.login.Login
import com.app.pataza.features.profile.register.Register
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserService
@Inject constructor(retrofit: Retrofit) : UserApi {
    private val userApi by lazy { retrofit.create(UserApi::class.java) }

    override fun login(request: Login.Request) = userApi.login(request)

    override fun appResources() = userApi.appResources()

    override fun getProfile() = userApi.getProfile()

    override fun doRegister(register: Register.Request) = userApi.doRegister(register)

    override fun editProfile(request: Profile.Request) = userApi.editProfile(request)
}
package com.app.pataza.features.login

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserService
@Inject constructor(retrofit: Retrofit) : UserApi {
    private val userApi by lazy { retrofit.create(UserApi::class.java) }

    override fun login(request: Login.Request) = userApi.login(request)

    override fun appResources() = userApi.appResources()
}
package com.app.pataza.features.login

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserService
@Inject constructor(retrofit: Retrofit) : UserApi {
    private val userApiApi by lazy { retrofit.create(UserApi::class.java) }

    override fun login(request: Login.Request) = userApiApi.login(request)
}
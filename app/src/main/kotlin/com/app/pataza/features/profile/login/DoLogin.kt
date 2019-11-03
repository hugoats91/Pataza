package com.app.pataza.features.profile.login

import com.app.pataza.core.interactor.UseCase
import com.app.pataza.data.user.UserRepository
import javax.inject.Inject

class DoLogin
@Inject constructor(private val userRepository: UserRepository) : UseCase<Boolean, DoLogin.Params>() {

    override suspend fun run(params: Params) = userRepository.doLogin(params.email, params.password, params.provider, params.type)

    data class Params(val email: String, val password: String?, val provider: String?, val type: String)
}
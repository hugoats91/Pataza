package com.app.pataza.features.login

import com.app.pataza.core.interactor.UseCase
import com.app.pataza.data.user.UserRepository
import javax.inject.Inject

class DoLogin
@Inject constructor(private val userRepository: UserRepository) : UseCase<User, DoLogin.Params>() {

    override suspend fun run(params: Params) = userRepository.doLogin(params.email, params.password)

    data class Params(val email: String, val password: String)
}
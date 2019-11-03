package com.app.pataza.features.profile.register

import com.app.pataza.core.interactor.UseCase
import com.app.pataza.data.user.UserRepository
import javax.inject.Inject

class DoRegister
@Inject constructor(private val userRepository: UserRepository) : UseCase<Boolean, DoRegister.Params>() {

    override suspend fun run(params: Params) = userRepository.doRegister(params.name, params.email, params.password, params.phone)

    data class Params(val name: String, val email: String, val password: String, val phone: String)
}
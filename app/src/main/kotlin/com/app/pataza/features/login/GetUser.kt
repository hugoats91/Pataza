package com.app.pataza.features.login

import com.app.pataza.core.interactor.UseCase
import com.app.pataza.data.user.UserRepository
import javax.inject.Inject

class GetUser
@Inject constructor(private val userRepository: UserRepository) : UseCase<User, UseCase.None>() {

    override suspend fun run(params: None) = userRepository.getUser()
}

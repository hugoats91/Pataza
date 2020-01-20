package com.app.pataza.features.profile

import com.app.pataza.core.interactor.UseCase
import com.app.pataza.data.user.UserRepository
import javax.inject.Inject

class DoLogout
@Inject constructor(private val userRepository: UserRepository) : UseCase<Boolean, UseCase.None>() {

    override suspend fun run(params: None) = userRepository.doLogout()
}
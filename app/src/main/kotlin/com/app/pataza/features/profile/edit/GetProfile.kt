package com.app.pataza.features.profile.edit

import com.app.pataza.core.interactor.UseCase
import com.app.pataza.data.user.UserRepository
import javax.inject.Inject

class GetProfile
@Inject constructor(private val userRepository: UserRepository) : UseCase<UserEditView, UseCase.None>() {

    override suspend fun run(params: None) = userRepository.getProfile()

}
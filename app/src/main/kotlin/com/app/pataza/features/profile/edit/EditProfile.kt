package com.app.pataza.features.profile.edit

import com.app.pataza.core.interactor.UseCase
import com.app.pataza.data.user.UserRepository
import javax.inject.Inject

class EditProfile
@Inject constructor(private val userRepository: UserRepository) : UseCase<Boolean, EditProfile.Params>() {

    override suspend fun run(params: Params) = userRepository.editProfile(params.name, params.phone, params.birthdate, params.address, params.country, params.latitude, params.longitude)

    data class Params(val name: String, val phone: String, val birthdate: String, val address: String, val country: String, val latitude: Double, val longitude: Double)
}
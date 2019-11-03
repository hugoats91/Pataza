package com.app.pataza.data.user

import com.app.pataza.PatazaApp
import com.app.pataza.core.exception.Failure
import com.app.pataza.core.exception.Failure.NoFoundUser
import com.app.pataza.core.functional.Either
import com.app.pataza.core.platform.NetworkHandler
import com.app.pataza.data.models.Resource
import com.app.pataza.features.profile.login.Login
import com.app.pataza.data.models.User
import com.app.pataza.features.profile.Profile
import com.app.pataza.features.profile.UserService
import com.app.pataza.features.profile.edit.UserEditView
import com.app.pataza.features.profile.register.Register
import javax.inject.Inject

interface UserRepository {
    fun getUser(): Either<Failure, User>
    fun doLogin(email: String, password: String?, provider: String?, type: String): Either<Failure, Boolean>
    fun doRegister(name: String, email: String, password: String, phone: String): Either<Failure, Boolean>
    fun getProfileAndResources(): Either<Failure, User>
    fun getProfile(): Either<Failure, UserEditView>
    fun editProfile(name: String, phone: String, birthdate: String, address: String, country: String, latitude: Double, longitude: Double): Either<Failure, Boolean>

    class Impl
    @Inject constructor(private val networkHandler: NetworkHandler,
                        private val userService: UserService) : UserRepository {
        override fun getUser(): Either<Failure, User> {
            PatazaApp.database.userDao().getUser().firstOrNull()?.toUser()?.let {
                return Either.Right(it)
            }
            return Either.Left(NoFoundUser)
        }

        override fun editProfile(name: String, phone: String, birthdate: String, address: String, country: String, latitude: Double, longitude: Double): Either<Failure, Boolean> {
            return when (networkHandler.isConnected) {
                true -> {
                    val editResponse = userService.editProfile(Profile.Request(name, phone, birthdate, address, country, latitude, longitude)).execute()
                    when (editResponse.isSuccessful) {
                        true -> {
                            editResponse.body()?.success?.let {
                                if(it) Either.Right(true) else Either.Left(Failure.ServerError)
                            }?: Either.Left(Failure.ServerError)
                        }
                        false -> Either.Left(Failure.ServerError)
                    }
                }
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun getProfile(): Either<Failure, UserEditView> {
            return when (networkHandler.isConnected) {
                true -> {
                    val profileResponse = userService.getProfile().execute()
                    when (profileResponse.isSuccessful) {
                        true -> {
                            profileResponse.body()?.data?.let {
                                PatazaApp.database.userDao().insert(it.toUserEntity())
                                it.country?.let { country ->
                                    val value = PatazaApp.database.resourceDao().getCountryById(country).firstOrNull()?.label
                                    val list = PatazaApp.database.resourceDao().getCountries().map { countryEntity ->  countryEntity.toCountry() }
                                    val edit = UserEditView(it, value, list)
                                    Either.Right(edit)
                                }?: Either.Left(Failure.ServerError)

                            }?: Either.Left(Failure.ServerError)
                        }
                        false -> Either.Left(Failure.ServerError)
                    }
                }
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun getProfileAndResources(): Either<Failure, User> {
            return when (networkHandler.isConnected) {
                true -> {
                    val profileResponse = userService.getProfile().execute()
                    when (profileResponse.isSuccessful) {
                        true -> {
                            val resourceResponse = userService.appResources().execute()
                            when (resourceResponse.isSuccessful) {
                                true -> {
                                    profileResponse.body()?.data?.let {
                                        PatazaApp.database.userDao().insert(it.toUserEntity())
                                        saveResources(resourceResponse.body()?.data)
                                        Either.Right(it)
                                    }?: Either.Left(Failure.ServerError)
                                }
                                else -> Either.Left(Failure.ServerError)
                            }
                        }
                        false -> Either.Left(Failure.ServerError)
                    }
                }
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun doLogin(email: String, password: String?, provider: String?, type: String): Either<Failure, Boolean> {
            return when (networkHandler.isConnected) {
                true -> {
                    val loginResponse = userService.login(Login.Request(email, password, provider, type)).execute()
                    when (loginResponse.isSuccessful) {
                        true -> {
                            loginResponse.body()?.data?.token?.let {
                                PatazaApp.prefs.token = it
                                PatazaApp.prefs.session = true
                                val resourceResponse = userService.appResources().execute()
                                val profileResponse = userService.getProfile().execute()
                                when (resourceResponse.isSuccessful&&profileResponse.isSuccessful) {
                                    true -> {
                                        resourceResponse.body()?.data?.let { resource ->  saveResources(resource) }
                                        profileResponse.body()?.data?.let { user -> PatazaApp.database.userDao().insert(user.toUserEntity()) }
                                        Either.Right(true)
                                    }
                                    else -> Either.Left(Failure.ServerError)
                                }
                            }?: Either.Left(Failure.ServerError)
                        }
                        false -> Either.Left(Failure.ServerError)
                    }
                }
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun doRegister(name: String, email: String, password: String, phone: String): Either<Failure, Boolean> {
            return when (networkHandler.isConnected) {
                true -> {
                    val registerResponse = userService.doRegister(Register.Request(name, email, password, phone)).execute()
                    when (registerResponse.isSuccessful) {
                        true -> {
                            registerResponse.body()?.data?.let {
                                PatazaApp.prefs.token = it.token
                                PatazaApp.prefs.session = true
                                val resourceResponse = userService.appResources().execute()
                                val profileResponse = userService.getProfile().execute()
                                when (resourceResponse.isSuccessful&&profileResponse.isSuccessful) {
                                    true -> {
                                        resourceResponse.body()?.data?.let { resource ->  saveResources(resource) }
                                        profileResponse.body()?.data?.let { user -> PatazaApp.database.userDao().insert(user.toUserEntity()) }
                                        Either.Right(true)
                                    }
                                    else -> Either.Left(Failure.ServerError)
                                }
                            }?: Either.Left(Failure.ServerError)
                        }
                        false -> Either.Left(Failure.ServerError)
                    }
                }
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        private fun saveResources(response: Resource?){
            response?.let {
                PatazaApp.database.resourceDao().insertAllPets(it.countries.map { country ->  country.toCountryEntity() })
                PatazaApp.database.resourceDao().insertAllRaces(it.races.map { race -> race.toRaceEntity() })
                PatazaApp.database.resourceDao().insertAllSizes(it.sizes.map { size -> size.toSizeEntity() })
                PatazaApp.database.resourceDao().insertAllDiseases(it.diseases.map { disease -> disease.toDiseaseEntity() })
                PatazaApp.database.resourceDao().insertAllVaccines(it.vaccines.map { vaccine ->  vaccine.toVaccineEntity() })
                PatazaApp.database.resourceDao().insertAllQualities(it.qualities.map { quality -> quality.toQualityEntity() })
            }
        }
    }
}
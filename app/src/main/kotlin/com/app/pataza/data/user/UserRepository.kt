package com.app.pataza.data.user

import com.app.pataza.PatazaApp
import com.app.pataza.core.exception.Failure
import com.app.pataza.core.exception.Failure.NoFoundUser
import com.app.pataza.core.functional.BaseResponse
import com.app.pataza.core.functional.Either
import com.app.pataza.core.platform.NetworkHandler
import com.app.pataza.data.models.Resource
import com.app.pataza.features.login.Login
import com.app.pataza.features.login.User
import com.app.pataza.features.login.UserService
import javax.inject.Inject

interface UserRepository {
    fun getUser(): Either<Failure, User>
    fun doLogin(email: String, password: String?, provider: String?, type: String): Either<Failure, Boolean>
    fun doRegister(): Either<Failure, User>

    class Impl
    @Inject constructor(private val networkHandler: NetworkHandler,
                        private val userService: UserService) : UserRepository {
        override fun getUser(): Either<Failure, User> {
            PatazaApp.database.userDao().getUser().firstOrNull()?.toUser()?.let {
                return Either.Right(it)
            }
            return Either.Left(NoFoundUser)
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
                                when (resourceResponse.isSuccessful) {
                                    true -> {
                                        saveResources(resourceResponse.body()?.data)
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

        private fun saveResources(response: Resource?): Either<Failure, Boolean>{
            response?.let {
                PatazaApp.database.resourceDao().insertAll(it.countries.map { country ->  country.toCountryEntity() })
                return Either.Right(true)
            }?: return Either.Left(Failure.ServerError)
        }

        override fun doRegister(): Either<Failure, User> {
            return Either.Left(Failure.ServerError)
        }
    }
}
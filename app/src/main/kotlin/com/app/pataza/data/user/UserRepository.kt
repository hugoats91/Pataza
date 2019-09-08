package com.app.pataza.data.user

import com.app.pataza.AndroidApplication
import com.app.pataza.core.exception.Failure
import com.app.pataza.core.exception.Failure.NoFoundUser
import com.app.pataza.core.functional.Either
import com.app.pataza.core.platform.NetworkHandler
import com.app.pataza.features.login.Login
import com.app.pataza.features.login.User
import com.app.pataza.features.login.UserService
import retrofit2.Call
import javax.inject.Inject

interface UserRepository {
    fun getUser(): Either<Failure, User>
    fun doLogin(email: String, password: String?, provider: String?, type: String): Either<Failure, Boolean>
    fun doRegister(): Either<Failure, User>

    class Impl
    @Inject constructor(private val networkHandler: NetworkHandler,
                        private val service: UserService) : UserRepository {
        override fun getUser(): Either<Failure, User> {
            AndroidApplication.database.userDao().getUser().firstOrNull()?.toUser()?.let {
                return Either.Right(it)
            }
            return Either.Left(NoFoundUser)
        }

        override fun doLogin(email: String, password: String?, provider: String?, type: String): Either<Failure, Boolean> {
            return when (networkHandler.isConnected) {
                true -> {
                    val loginResponse = service.login(Login.Request(email, password, provider, type)).execute()
                    when (loginResponse.isSuccessful) {
                        true -> Either.Right(true)
                        false -> Either.Left(Failure.ServerError)
                    }
                }
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun doRegister(): Either<Failure, User> {
            return Either.Left(Failure.ServerError)
        }
    }
}
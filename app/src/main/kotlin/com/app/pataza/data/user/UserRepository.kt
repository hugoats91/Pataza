package com.app.pataza.data.user

import com.app.pataza.AndroidApplication
import com.app.pataza.core.exception.Failure
import com.app.pataza.core.exception.Failure.NoFoundUser
import com.app.pataza.core.functional.Either
import com.app.pataza.features.login.User
import javax.inject.Inject

interface UserRepository {
    fun getUser(): Either<Failure, User>
    fun doLogin(email: String, password: String): Either<Failure, User>
    fun doRegister(): Either<Failure, User>

    class Local
    @Inject constructor() : UserRepository {
        override fun getUser(): Either<Failure, User> {
            AndroidApplication.database.userDao().getUser().firstOrNull()?.toUser()?.let {
                return Either.Right(it)
            }
            return Either.Left(NoFoundUser)
        }

        override fun doLogin(email: String, password: String): Either<Failure, User> {
            AndroidApplication.database.userDao().getUser().firstOrNull()?.toUser()?.let {
                return Either.Right(it)
            }
            return Either.Left(NoFoundUser)
        }

        override fun doRegister(): Either<Failure, User> {
            return Either.Left(Failure.ServerError)
        }
    }
}
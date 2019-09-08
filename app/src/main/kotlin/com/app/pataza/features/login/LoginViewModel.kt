package com.app.pataza.features.login

import android.arch.lifecycle.MutableLiveData
import com.app.pataza.core.platform.BaseViewModel
import javax.inject.Inject

class LoginViewModel
@Inject constructor(private val doLogin: DoLogin) : BaseViewModel() {

    var userView: MutableLiveData<User> = MutableLiveData()

    fun doLogin(email: String, password: String) = doLogin(DoLogin.Params(email, password)) { it.either(::handleFailure, ::handleLogin) }

    private fun handleLogin(user: User) {
        this.userView.value = user
    }
}
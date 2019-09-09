package com.app.pataza.features.login

import androidx.lifecycle.MutableLiveData
import com.app.pataza.core.platform.BaseViewModel
import javax.inject.Inject

class LoginViewModel
@Inject constructor(private val doLogin: DoLogin) : BaseViewModel() {

    var success: MutableLiveData<Boolean> = MutableLiveData()

    fun doLogin(email: String, password: String?, provider: String?, type: String) = doLogin(DoLogin.Params(email, password, provider, type)) { it.either(::handleFailure, ::handleLogin) }

    private fun handleLogin(success: Boolean) {
        this.success.value = success
    }
}
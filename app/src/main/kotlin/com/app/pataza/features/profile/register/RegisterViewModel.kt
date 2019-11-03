package com.app.pataza.features.profile.register

import androidx.lifecycle.MutableLiveData
import com.app.pataza.core.platform.BaseViewModel
import javax.inject.Inject

class RegisterViewModel
@Inject constructor(private val doRegister: DoRegister) : BaseViewModel() {

    var success: MutableLiveData<Boolean> = MutableLiveData()

    fun register(name: String, email: String, password: String, phone: String) = doRegister(DoRegister.Params(name, email, password, phone)) { it.either(::handleFailure, ::handleLogin) }

    private fun handleLogin(success: Boolean) {
        this.success.value = success
    }
}
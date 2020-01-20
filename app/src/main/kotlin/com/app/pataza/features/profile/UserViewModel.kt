package com.app.pataza.features.profile

import androidx.lifecycle.MutableLiveData
import com.app.pataza.core.interactor.UseCase
import com.app.pataza.core.platform.BaseViewModel
import com.app.pataza.features.profile.edit.EditProfile
import com.app.pataza.features.profile.edit.GetProfile
import com.app.pataza.features.profile.edit.UserEditView
import com.app.pataza.features.profile.login.DoLogin
import javax.inject.Inject

class UserViewModel
@Inject constructor(private val doLogin: DoLogin, private val editProfile: EditProfile, private val getProfile: GetProfile, private val doLogout: DoLogout) : BaseViewModel() {

    var success: MutableLiveData<Boolean> = MutableLiveData()
    var successLogout: MutableLiveData<Boolean> = MutableLiveData()
    var userView: MutableLiveData<UserEditView> = MutableLiveData()
    var edit: MutableLiveData<Boolean> = MutableLiveData()

    fun doLogout() = doLogout(UseCase.None()){ it.either(::handleFailure, ::handleLogout)}

    fun doLogin(email: String, password: String?, provider: String?, type: String) = doLogin(DoLogin.Params(email, password, provider, type)) { it.either(::handleFailure, ::handleLogin) }

    fun getProfile() = getProfile(UseCase.None()){ it.either(::handleFailure, ::handleProfile) }

    fun editProfile(name: String, phone: String, birthdate: String, address: String, country: String, latitude: Double, longitude: Double) = editProfile(EditProfile.Params(name, phone, birthdate, address, country, latitude, longitude)) {it.either(::handleFailure, ::handleEdit)}

    private fun handleLogin(success: Boolean) {
        this.success.value = success
    }

    private fun handleLogout(success: Boolean){
        this.successLogout.value = success
    }

    private fun handleProfile(userView: UserEditView) {
        this.userView.value = userView
    }

    private fun handleEdit(edit: Boolean) {
        this.edit.value = edit
    }
}
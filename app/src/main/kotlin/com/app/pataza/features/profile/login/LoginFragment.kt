package com.app.pataza.features.profile.login

import android.os.Bundle
import android.view.View
import com.app.pataza.R
import com.app.pataza.core.extension.failure
import com.app.pataza.core.extension.observe
import com.app.pataza.core.extension.viewModel
import com.app.pataza.core.platform.BaseFragment
import com.app.pataza.features.profile.UserViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {
    override fun layoutId() = R.layout.fragment_login

    private lateinit var userViewModel: UserViewModel

    private fun initViewModel() {
        userViewModel = viewModel(viewModelFactory) {
            observe(success, ::successLogin)
            failure(failure, ::handleBaseFailure)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        initViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnRegister.setOnClickListener { navigator.showRegister(context) }
        btLogin.setOnClickListener { goLogin() }

    }

    private fun successLogin(success: Boolean?) {
        success?.let {
            navigator.showMenu(context)
        }
    }

    private fun goLogin() {
        if (validate()) userViewModel.doLogin(edEmail.text.toString().trim(), edPassword.text.toString().trim(), null, Login.Request.REGULAR) else notify(R.string.failure_server_error)
    }

    private fun validate() = edEmail.text.isNotBlank() && edPassword.text.isNotBlank()
}

/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.app.pataza.features.login

import android.os.Bundle
import android.view.View
import com.app.pataza.R
import com.app.pataza.core.extension.failure
import com.app.pataza.core.extension.observe
import com.app.pataza.core.extension.viewModel
import com.app.pataza.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {
    override fun layoutId() = R.layout.fragment_login

    private lateinit var loginViewModel: LoginViewModel

    private fun initViewModel() {
        loginViewModel = viewModel(viewModelFactory) {
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
        if (validate()) loginViewModel.doLogin(edEmail.text.toString().trim(), edPassword.text.toString().trim(), null, Login.Request.REGULAR) else notify(R.string.failure_server_error)
    }

    private fun validate() = edEmail.text.isNotBlank() && edPassword.text.isNotBlank()
}

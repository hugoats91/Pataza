package com.app.pataza.features.register

import android.os.Bundle
import android.view.View
import com.app.pataza.R
import com.app.pataza.core.extension.failure
import com.app.pataza.core.extension.observe
import com.app.pataza.core.extension.viewModel
import com.app.pataza.core.platform.BaseFragment
import com.app.pataza.features.login.LoginViewModel
import com.app.pataza.features.login.User
import kotlinx.android.synthetic.main.fragment_login.*

class RegisterFragment : BaseFragment(), View.OnClickListener {
    override fun layoutId() = R.layout.fragment_login

    private lateinit var loginViewModel: LoginViewModel

    private fun initViewModel(){
        loginViewModel = viewModel(viewModelFactory) {
            observe(userView, ::showUser)
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

    }

    private fun showUser(user: User?){
        user?.let {

        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btLogin -> {
                if(validate()) loginViewModel.doLogin(edEmail.text.toString(), edPassword.text.toString()) else notify(R.string.failure_server_error)
            }
        }
    }

    private fun validate() = edEmail.text.isNotBlank() && edPassword.text.isNotBlank()
}
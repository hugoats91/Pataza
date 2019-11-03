package com.app.pataza.features.profile.register

import android.os.Bundle
import android.view.View
import com.app.pataza.R
import com.app.pataza.core.extension.failure
import com.app.pataza.core.extension.observe
import com.app.pataza.core.extension.viewModel
import com.app.pataza.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : BaseFragment() {
    override fun layoutId() = R.layout.fragment_register

    private lateinit var registerViewModel: RegisterViewModel

    private fun initViewModel(){
        registerViewModel = viewModel(viewModelFactory) {
            observe(success, ::successRegister)
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
        btEditar.setOnClickListener { registerUser() }
    }

    private fun registerUser(){
        if(validate()){
            registerViewModel.register(etName.text.toString(), etBirthdate.text.toString(), etPassword.text.toString(), etPhone.text.toString())
        }else{
            notify(R.string.register_error_request)
        }
    }

    private fun successRegister(success: Boolean?){
        success?.let {
            navigator.showMenu(context)
        }
    }

    private fun validate() = etBirthdate.text.isNotBlank() && etName.text.isNotBlank() && etPassword.text.isNotBlank() && etPhone.text.isNotBlank()
}
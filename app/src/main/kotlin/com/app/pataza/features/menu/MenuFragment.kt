package com.app.pataza.features.menu

import android.os.Bundle
import android.view.View
import com.app.pataza.R
import com.app.pataza.core.platform.BaseFragment
import com.app.pataza.features.login.LoginViewModel

class MenuFragment : BaseFragment() {
    override fun layoutId() = R.layout.fragment_menu

    private lateinit var loginViewModel: LoginViewModel

    private fun initViewModel() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        initViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
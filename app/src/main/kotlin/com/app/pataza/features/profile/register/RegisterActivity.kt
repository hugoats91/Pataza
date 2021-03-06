package com.app.pataza.features.profile.register

import android.content.Context
import android.content.Intent
import com.app.pataza.core.platform.BaseActivity

class RegisterActivity : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, RegisterActivity::class.java)
    }

    override fun fragment() = RegisterFragment()

    override fun showToolbar() = false
}
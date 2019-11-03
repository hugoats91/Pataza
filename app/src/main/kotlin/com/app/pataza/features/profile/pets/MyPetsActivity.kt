package com.app.pataza.features.profile.pets

import android.content.Context
import android.content.Intent
import com.app.pataza.core.platform.BaseActivity

class MyPetsActivity : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, MyPetsActivity::class.java)
    }

    override fun fragment() = MyPetsFragment()

    override fun showToolbar() = false
}
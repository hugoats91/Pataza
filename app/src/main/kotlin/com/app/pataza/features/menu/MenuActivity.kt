package com.app.pataza.features.menu

import android.content.Context
import android.content.Intent
import com.app.pataza.core.platform.BaseActivity

class MenuActivity : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, MenuActivity::class.java)
    }

    override fun fragment() = MenuFragment()

    override fun showToolbar() = false
}
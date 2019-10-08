package com.app.pataza.features.pets.add

import android.content.Context
import android.content.Intent
import com.app.pataza.core.platform.BaseActivity

class AddPetActivity : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, AddPetActivity::class.java)
    }

    override fun fragment() = AddPetFragment()

    override fun showToolbar() = false
}
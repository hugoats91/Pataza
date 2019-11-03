package com.app.pataza.features.profile.edit

import android.content.Context
import android.content.Intent
import com.app.pataza.core.platform.BaseActivity

class EditProfileActivity : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, EditProfileActivity::class.java)
    }

    override fun fragment() = EditProfileFragment()

    override fun showToolbar() = false
}
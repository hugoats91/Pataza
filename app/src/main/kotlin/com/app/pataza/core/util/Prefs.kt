package com.app.pataza.core.util

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Prefs @Inject constructor(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

    companion object{
        const val PREFS_NAME = "com.app.pataza"
        const val TOKEN = "token"
        const val SESSION = "session"
        const val COUNTRY = "country"
    }

    var token: String
        get() = prefs.getString(TOKEN, "")!!
        set(value) = prefs.edit().putString(TOKEN, value).apply()

    var country: String
        get() = prefs.getString(COUNTRY, "")!!
        set(value) = prefs.edit().putString(COUNTRY, value).apply()

    var session: Boolean
        get() = prefs.getBoolean(SESSION, false)
        set(value) = prefs.edit().putBoolean(SESSION, value).apply()
}
package com.app.pataza.features.profile.edit

import com.app.pataza.data.models.Country
import com.app.pataza.data.models.User

data class UserEditView(val user: User, val country: String?, val countries: List<Country>)
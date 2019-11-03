package com.app.pataza.features.profile.pets

import com.app.pataza.data.models.Pet

class MyPetsRemote{
    data class Response(val pets: List<Pet>)
}
package com.app.pataza.features.profile.register

class Register {
    data class Request(val name: String, val email: String, val password: String, val phone: String)
    data class Response(val name: String, val token: String)
}
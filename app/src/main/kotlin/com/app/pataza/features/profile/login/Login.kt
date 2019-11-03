package com.app.pataza.features.profile.login

class Login{
    data class Request(val email: String, val password: String?, val provider: String?, val type: String){
        companion object{
            const val REGULAR = "regular"
            const val SOCIAL = "social"
        }
    }
    data class Response(val name: String, val token: String)
}
package com.app.pataza.core.di

import com.app.pataza.PatazaApp
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor() : Interceptor {
    var token: String = ""
    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()

        if (original.url().encodedPath().contains("user/login") && original.method() == "post"
                || (original.url().encodedPath().contains("user/signup") && original.method() == "post")
        ) {
            return chain.proceed(original)
        }
        token = PatazaApp.prefs.token
        val originalHttpUrl = original.url()
        val requestBuilder = original.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .url(originalHttpUrl)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
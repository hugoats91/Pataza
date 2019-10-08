package com.app.pataza.core.di

import android.content.Context
import com.app.pataza.PatazaApp
import com.app.pataza.data.user.UserRepository
import com.app.pataza.features.movies.MoviesRepository
import com.app.pataza.features.pets.PetRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: PatazaApp) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://206.189.206.133/v1/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(
                HttpLoggingInterceptor.Logger { message -> Timber.tag("NETWORK: ").i(message) })
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return interceptor
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(tokenInterceptor: TokenInterceptor, httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder().addInterceptor(
            httpLoggingInterceptor).addInterceptor(tokenInterceptor).build()

    @Provides
    @Singleton
    fun provideMoviesRepository(dataSource: MoviesRepository.Network): MoviesRepository = dataSource

    @Provides
    @Singleton
    fun provideUserRepository(dataSource: UserRepository.Impl): UserRepository = dataSource

    @Provides
    @Singleton
    fun providePetRepository(dataSource: PetRepository.Impl): PetRepository = dataSource
}

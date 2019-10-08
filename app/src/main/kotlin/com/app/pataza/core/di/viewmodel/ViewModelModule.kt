package com.app.pataza.core.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.pataza.features.login.LoginViewModel
import com.app.pataza.features.movies.MovieDetailsViewModel
import com.app.pataza.features.movies.MoviesViewModel
import com.app.pataza.features.pets.PetViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    abstract fun bindsMoviesViewModel(moviesViewModel: MoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    abstract fun bindsMovieDetailsViewModel(movieDetailsViewModel: MovieDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindsLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PetViewModel::class)
    abstract fun bindsPetViewModel(petViewModel: PetViewModel): ViewModel
}
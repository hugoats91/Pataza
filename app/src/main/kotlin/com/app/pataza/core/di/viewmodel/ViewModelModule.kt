package com.app.pataza.core.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.pataza.features.profile.UserViewModel
import com.app.pataza.features.movies.MovieDetailsViewModel
import com.app.pataza.features.movies.MoviesViewModel
import com.app.pataza.features.pets.PetViewModel
import com.app.pataza.features.pets.add.AddPetViewModel
import com.app.pataza.features.profile.pets.PetsUserViewModel
import com.app.pataza.features.profile.register.RegisterViewModel
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
    @ViewModelKey(UserViewModel::class)
    abstract fun bindsLoginViewModel(userViewModel: UserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PetViewModel::class)
    abstract fun bindsPetViewModel(petViewModel: PetViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddPetViewModel::class)
    abstract fun bindsAddPetViewModel(addPetViewModel: AddPetViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PetsUserViewModel::class)
    abstract fun bindsPetsUserViewModel(petsUserViewModel: PetsUserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindsRegisterViewModel(registerViewModel: RegisterViewModel): ViewModel
}
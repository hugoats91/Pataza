package com.app.pataza.core.di

import com.app.pataza.PatazaApp
import com.app.pataza.core.di.viewmodel.ViewModelModule
import com.app.pataza.features.movies.MovieDetailsFragment
import com.app.pataza.features.movies.MoviesFragment
import com.app.pataza.core.navigation.RouteActivity
import com.app.pataza.features.profile.ProfileFragment
import com.app.pataza.features.profile.login.LoginFragment
import com.app.pataza.features.menu.MenuFragment
import com.app.pataza.features.pets.PetListFragment
import com.app.pataza.features.pets.add.AddPetFragment
import com.app.pataza.features.profile.edit.EditProfileFragment
import com.app.pataza.features.profile.pets.MyPetsFragment
import com.app.pataza.features.profile.register.RegisterFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: PatazaApp)
    fun inject(routeActivity: RouteActivity)

    fun inject(moviesFragment: MoviesFragment)
    fun inject(movieDetailsFragment: MovieDetailsFragment)
    fun inject(loginFragment: LoginFragment)
    fun inject(registerFragment: RegisterFragment)
    fun inject(menuFragment: MenuFragment)
    fun inject(petListFragment: PetListFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(addPetFragment: AddPetFragment)
    fun inject(editProfileFragment: EditProfileFragment)
    fun inject(myPetsFragment: MyPetsFragment)
}

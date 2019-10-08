/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.app.pataza.core.di

import com.app.pataza.PatazaApp
import com.app.pataza.core.di.viewmodel.ViewModelModule
import com.app.pataza.features.movies.MovieDetailsFragment
import com.app.pataza.features.movies.MoviesFragment
import com.app.pataza.core.navigation.RouteActivity
import com.app.pataza.features.profile.ProfileFragment
import com.app.pataza.features.login.LoginFragment
import com.app.pataza.features.menu.MenuFragment
import com.app.pataza.features.pets.PetListFragment
import com.app.pataza.features.pets.add.AddPetFragment
import com.app.pataza.features.register.RegisterFragment
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
}

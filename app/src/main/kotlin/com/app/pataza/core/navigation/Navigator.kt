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
package com.app.pataza.core.navigation

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentActivity
import android.view.View
import android.widget.ImageView
import com.app.pataza.features.profile.login.Authenticator
import com.app.pataza.features.profile.login.LoginActivity
import com.app.pataza.features.movies.MovieDetailsActivity
import com.app.pataza.features.movies.MovieView
import com.app.pataza.core.extension.empty
import com.app.pataza.features.menu.MenuActivity
import com.app.pataza.features.pets.add.AddPetActivity
import com.app.pataza.features.profile.edit.EditProfileActivity
import com.app.pataza.features.profile.pets.MyPetsActivity
import com.app.pataza.features.profile.register.RegisterActivity
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.IntentCompat
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK




@Singleton
class Navigator
@Inject constructor(private val authenticator: Authenticator) {

    private fun showLogin(context: Context) = context.startActivity(LoginActivity.callingIntent(context))

    fun showMain(context: Context?) {
        context?.let {
            when (authenticator.userLoggedIn()) {
                true -> showMenu(it)
                false -> showLogin(it)
            }
        }
    }

    fun returnLogin(context: Context?) {
        context?.let {
            val intent = Intent(it, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            it.startActivity(intent)
        }
    }

    fun showMyPets(context: Context?){ context?.let { it.startActivity(MyPetsActivity.callingIntent(it)) }}
    fun showEditProfile(context: Context?){ context?.let { it.startActivity(EditProfileActivity.callingIntent(it)) } }
    fun showAddPet(context: Context?){ context?.let { it.startActivity(AddPetActivity.callingIntent(it)) } }
    fun showRegister(context: Context?){ context?.let { it.startActivity(RegisterActivity.callingIntent(it)) } }
    fun showMenu(context: Context?){ context?.let { it.startActivity(MenuActivity.callingIntent(it)) }}

    fun showMovieDetails(activity: FragmentActivity, movie: MovieView, navigationExtras: Extras) {
        val intent = MovieDetailsActivity.callingIntent(activity, movie)
        val sharedView = navigationExtras.transitionSharedElement as ImageView
        val activityOptions = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, sharedView, sharedView.transitionName)
        activity.startActivity(intent, activityOptions.toBundle())
    }

    private val VIDEO_URL_HTTP = "http://www.youtube.com/watch?v="
    private val VIDEO_URL_HTTPS = "https://www.youtube.com/watch?v="

    fun openVideo(context: Context, videoUrl: String) {
        try {
            context.startActivity(createYoutubeIntent(videoUrl))
        } catch (ex: ActivityNotFoundException) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl)))
        }
    }

    private fun createYoutubeIntent(videoUrl: String): Intent {
        val videoId = when {
            videoUrl.startsWith(VIDEO_URL_HTTP) -> videoUrl.replace(VIDEO_URL_HTTP, String.empty())
            videoUrl.startsWith(VIDEO_URL_HTTPS) -> videoUrl.replace(VIDEO_URL_HTTPS, String.empty())
            else -> videoUrl
        }

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
        intent.putExtra("force_fullscreen", true)

        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.M)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        return intent
    }

    class Extras(val transitionSharedElement: View)
}



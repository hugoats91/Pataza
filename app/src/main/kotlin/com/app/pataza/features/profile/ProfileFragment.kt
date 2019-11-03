package com.app.pataza.features.profile

import android.os.Bundle
import android.view.View
import com.app.pataza.R
import com.app.pataza.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*

//AIzaSyB4uRwUBpGH1ERS_SDUYQCWaQgLQWwAw8Q
class ProfileFragment : BaseFragment() {
    override fun layoutId() = R.layout.fragment_profile

    private fun initViewModel() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        initViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btEditProfile.setOnClickListener { editProfile() }
        btMyPets.setOnClickListener { myPets() }
        btnAdd.setOnClickListener { navigator.showAddPet(context) }
    }

    private fun editProfile(){
        navigator.showEditProfile(context)
    }

    private fun myPets(){
        navigator.showMyPets(context)
    }

}
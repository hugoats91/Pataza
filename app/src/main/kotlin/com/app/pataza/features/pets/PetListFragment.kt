package com.app.pataza.features.pets

import android.os.Bundle
import com.app.pataza.R
import com.app.pataza.core.platform.BaseFragment

class PetListFragment : BaseFragment() {
    override fun layoutId() = R.layout.fragment_pet_list

    private fun initViewModel() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        initViewModel()
    }
}
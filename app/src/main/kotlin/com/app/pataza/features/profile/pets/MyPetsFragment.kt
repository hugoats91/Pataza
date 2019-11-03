package com.app.pataza.features.profile.pets

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.pataza.R
import com.app.pataza.core.extension.failure
import com.app.pataza.core.extension.observe
import com.app.pataza.core.extension.viewModel
import com.app.pataza.core.platform.BaseFragment
import com.app.pataza.features.pets.PetView
import kotlinx.android.synthetic.main.fragment_my_pets.*

class MyPetsFragment : BaseFragment() {
    private var adapter: MyPetsAdapter? = null
    private lateinit var petUserViewModel: PetsUserViewModel

    override fun layoutId() = R.layout.fragment_my_pets

    private fun initViewModel() {

        petUserViewModel = viewModel(viewModelFactory) {
            observe(pets, ::showPets)
            failure(failure, ::handleBaseFailure)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        initViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btAdd.setOnClickListener { addPet() }
        rvPets.layoutManager = LinearLayoutManager(context)
        adapter = MyPetsAdapter()
        rvPets.adapter = adapter
        petUserViewModel.petsByUser()
    }

    private fun showPets(list: List<PetView>?) {
        list?.let {
            adapter?.collection = it
        }
    }

    private fun addPet(){
        navigator.showAddPet(context)
    }
}
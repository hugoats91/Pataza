package com.app.pataza.features.menu

import android.os.Bundle
import android.view.View
import com.app.pataza.R
import com.app.pataza.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_menu.*



class MenuFragment : BaseFragment() {
    override fun layoutId() = R.layout.fragment_menu

    private fun initViewModel() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        initViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            fragmentManager?.let {mng ->
                vpMenu.adapter = MenuAdapter(mng, it)
                tbMenu.setupWithViewPager(vpMenu)
                loadIconTab()
            }
        }
    }

    private fun loadIconTab(){
        intArrayOf(R.drawable.perfil, R.drawable.inicio, R.drawable.favoritos, R.drawable.tips).forEachIndexed { index, i ->
            tbMenu.getTabAt(index)?.setIcon(i)
        }

    }
}
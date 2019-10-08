package com.app.pataza.features.menu

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.app.pataza.features.profile.ProfileFragment
import com.app.pataza.features.pets.PetListFragment


class MenuAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {
    companion object {
        const val PAGE_COUNT = 4
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> ProfileFragment()
            1 -> PetListFragment()
            else -> ProfileFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return null
    }
}
package com.app.pataza.features.menu

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.app.pataza.features.login.LoginFragment


class MenuAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {
    companion object{
        const val PAGE_COUNT = 4
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getItem(position: Int): Fragment {
        return LoginFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return null
    }
}
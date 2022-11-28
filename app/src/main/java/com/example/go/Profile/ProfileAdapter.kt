package com.example.go.Profile

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class ProfileAdapter(fm: FragmentManager, private val uid: String) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        val fragment = when(position)
        {
            0 -> ProfileImagePostFragment(uid).newInstant()
            1 -> ProfilePostFragment(uid).newInstant()
            else -> ProfileImagePostFragment(uid).newInstant()
        }
        return fragment
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        val title = when(position)
        {
            0 -> "ImagePost"
            1 -> "Post"
            else -> "main"
        }
        return title
    }
}
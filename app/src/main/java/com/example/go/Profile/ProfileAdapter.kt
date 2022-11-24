package com.example.go.Profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.go.Search.SearchFragment


class ProfileAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        val fragment = when(position)
        {
            0 -> ProfileImagePostFragment().newInstant()
            1 -> ProfilePostFragment().newInstant()
            else -> ProfileImagePostFragment().newInstant()
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
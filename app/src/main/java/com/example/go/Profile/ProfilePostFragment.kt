package com.example.go.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.go.R

class ProfilePostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_post, container, false)
    }

    fun newInstant() : ProfilePostFragment {
        val args = Bundle()
        val frag = ProfilePostFragment()
        frag.arguments = args
        return frag
    }
}
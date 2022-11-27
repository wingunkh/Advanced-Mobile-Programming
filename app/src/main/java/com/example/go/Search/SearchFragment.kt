package com.example.go.Search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.go.MainActivity
import com.example.go.PostViewModel
import com.example.go.Profile.ProfileFragment
import com.example.go.Utils.FBRef
import com.example.go.databinding.FragmentSearchBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class SearchFragment : Fragment(){

    private lateinit var binding: FragmentSearchBinding
    private val viewModel by activityViewModels<PostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query!=null){
                    FBRef.userRef.child(query).addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if(query==dataSnapshot.child("uid").value.toString()){
                                Log.d("From SearchFragment : ","해당 유저가 존재합니다!")
                                search(query)
                            }
                            else{
                                search("none")
                            }
                        }
                        override fun onCancelled(error: DatabaseError) { } })
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // 검색창에서 글자가 변경이 일어날 때마다 호출

                return true
            }
        })

        return binding.root
    }

    private fun search(query:String) {

        binding.searchList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = SearchAdapter(viewModel,query) {
                (activity as MainActivity).changeFragmentWithBackStack(ProfileFragment.newInstance())
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
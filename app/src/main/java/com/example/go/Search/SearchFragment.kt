package com.example.go.Search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.go.PostViewModel
import com.example.go.databinding.FragmentSearchBinding

private const val POSITION = "position"

class SearchFragment : Fragment(){

    private lateinit var binding: FragmentSearchBinding
    private val viewModel by activityViewModels<PostViewModel>()

    private var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentSearchBinding.inflate(inflater, container, false)

        arguments?.let {
            position = it.getInt(POSITION)
        }


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                // 검색 버튼 누를 때 호출
//                if(query!=null){
//                    FBRef.userRef.child("9c9c1dEYJEGFYvvXWM0HE71eelk8t1").addListenerForSingleValueEvent(object :
//                        ValueEventListener {
//                        override fun onDataChange(dataSnapshot: DataSnapshot) {
//                            //val map = dataSnapshot?.key as Map<*, *>?
//                            //val uid = map?.get("uid")?.toString()
//                            if(query == dataSnapshot?.key.toString())
//                                Log.d("씨발", "해당 UID를 지닌 사용자가 존재합니다!")
//                        }
//                        override fun onCancelled(error: DatabaseError) { } })
//                }


                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                // 검색창에서 글자가 변경이 일어날 때마다 호출

                return true
            }
        })

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
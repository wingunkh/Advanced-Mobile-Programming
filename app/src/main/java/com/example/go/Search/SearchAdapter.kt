package com.example.go.Search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.go.PostViewModel
import com.example.go.Utils.FBRef
import com.example.go.databinding.ItemSearchProfileBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class SearchAdapter(private val viewModel: PostViewModel, private val query:String, private val itemClicked: (uid:String) -> Unit) : RecyclerView.Adapter<SearchAdapter.SearchAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapterViewHolder {
        val binding = ItemSearchProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchAdapterViewHolder(binding)
    }

    inner class SearchAdapterViewHolder(val binding: ItemSearchProfileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                if(query!=null) {
                        if(query=="none"){
                            itemSearchUserProfile.visibility= View.GONE
                            itemSearchUsername.visibility=View.GONE
                            noUser.visibility=View.VISIBLE
                        }
                        FBRef.userRef.child(query).addValueEventListener(object :
                            ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                if(query == dataSnapshot.child("uid").value.toString()){
                                    itemSearchUsername.text = dataSnapshot.child("displayName").value.toString()
                                    Log.d("query is ", query)
                                    Log.d("From SearchAdapter : ","해당 유저가 존재합니다!")
                                }
                            }
                            override fun onCancelled(error: DatabaseError) { } })
                }
                itemSearchProfileView.setOnClickListener {
                    itemClicked(query)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: SearchAdapterViewHolder, position: Int) {
            holder.bind()
    }

    override fun getItemCount() : Int = 1
}
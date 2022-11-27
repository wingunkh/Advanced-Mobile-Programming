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

class SearchAdapter(private val viewModel: PostViewModel, private val query:String, private val itemClicked: (position: Int) -> Unit) : RecyclerView.Adapter<SearchAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemSearchProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    inner class PostViewHolder(val binding: ItemSearchProfileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                if(query!=null) {
                        if(query=="none"){
                            itemSearchUserProfile.visibility= View.GONE
                            itemSearchUsername.visibility=View.GONE
                            noUser.visibility=View.VISIBLE
                        }
                        FBRef.userRef.child(query).addListenerForSingleValueEvent(object :
                            ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                if(query == dataSnapshot.child("uid").value.toString()){
                                    itemSearchUsername.text = dataSnapshot.child("displayName").value.toString()
                                    Log.d("From SearchAdapter : ","해당 유저가 존재합니다!")
                                }
                            }
                            override fun onCancelled(error: DatabaseError) { } })
                }
                itemSearchProfileView.setOnClickListener {
                    itemClicked(adapterPosition)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
            holder.bind()
    }

    override fun getItemCount() : Int = 1
}
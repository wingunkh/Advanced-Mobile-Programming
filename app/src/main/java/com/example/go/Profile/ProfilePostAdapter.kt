package com.example.go.Post

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.go.PostViewModel
import com.example.go.databinding.ItemPostBinding
import com.example.go.Model.TextPost
import com.example.go.Utils.FBAuth
import com.example.go.Utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ProfilePostAdapter(private val viewModel: PostViewModel, private val uid: String) : RecyclerView.Adapter<ProfilePostAdapter.ProfilePostViewHolder>() {
    private lateinit var binding : ItemPostBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilePostViewHolder {
        binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("uid from CreateViewHolder : ", uid)
        return ProfilePostViewHolder(binding)
    }

    inner class ProfilePostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: TextPost) {
            if(uid!=null) {
                FBRef.postRef.child(uid).addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if(uid == dataSnapshot.child("uid").value.toString()){
                            binding.apply {
                                itemPostTitleText.text = post.title
                                itemPostUsername.text = post.username
                                itemPostDate.text = post.date
                            }
                            Log.d("uid is ", uid)
                        }
                    }
                    override fun onCancelled(error: DatabaseError) { } })
            }
        }
    }

    override fun onBindViewHolder(holder: ProfilePostViewHolder, position: Int) {
        holder.bind(viewModel.textPostLiveData.value!![position])
    }

    override fun getItemCount() : Int {
        var item = 0
        FBRef.postRef.child(uid).addValueEventListener(object :
            ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (uid == snapshot.child("uid").value.toString()) {
                    item ++;
                }
            } override fun onCancelled(error: DatabaseError) {}
        })
        Log.d("item is ", item.toString())
        return item
    }

}
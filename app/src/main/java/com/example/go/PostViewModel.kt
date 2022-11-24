package com.example.go

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.go.Model.ImagePost
import com.example.go.Model.TextPost
import com.example.go.Utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class PostViewModel : ViewModel() {

    private val textPostList = mutableListOf<TextPost>()
    private val _textPostLiveData = MutableLiveData<List<TextPost>>()
    val textPostLiveData: LiveData<List<TextPost>> get() = _textPostLiveData

    private val imagePostList = mutableListOf<ImagePost>()
    private val _imagePostLiveData = MutableLiveData<List<ImagePost>>()
    val imagePostLiveData: LiveData<List<ImagePost>> get() = _imagePostLiveData

    init {
        initTextPostList()
        initImagePostList()
    }

    fun initTextPostList() {

        FBRef.postRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                textPostList.clear()
                if(snapshot.exists()) {
                    for (data in snapshot.children) {
                        val getData = data.getValue(TextPost::class.java)
                        textPostList.add(getData!!)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", error.toException())
            }
        })

        _textPostLiveData.value = textPostList
    }

    fun initImagePostList() {

        FBRef.imagePostRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                imagePostList.clear()
                if(snapshot.exists()) {
                    for (data in snapshot.children) {
                        val getData = data.getValue(ImagePost::class.java)
                        imagePostList.add(getData!!)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", error.toException())
            }
        })

        _imagePostLiveData.value = imagePostList
    }

    fun createTextPostItem(newPostKey: String, textPost: TextPost) {
        FBRef.postRef
            .child(newPostKey).setValue(textPost)
        textPostList.add(textPost)
        _textPostLiveData.value = textPostList
    }

    fun createImagePostItem(newPostKey: String, imagePost: ImagePost) {
        FBRef.imagePostRef
            .child(newPostKey).setValue(imagePost)
        imagePostList.add(imagePost)
        _imagePostLiveData.value = imagePostList
    }
}
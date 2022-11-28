package com.example.go

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.go.Model.*
import com.example.go.Utils.FBAuth
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

    private fun initTextPostList() {

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                textPostList.clear()
                if(snapshot.exists()) {
                    for (data in snapshot.children) {
                        Log.d(TAG,"textPost : " + data.value)
                        val getData = data.getValue(TextPost::class.java)
                        textPostList.add(getData!!)
                        textPostList.reverse()
                        _textPostLiveData.value = textPostList
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", error.toException())
            }
        }

        FBRef.postRef.addValueEventListener(postListener)
    }

    private fun initImagePostList() {

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                imagePostList.clear()
                if(snapshot.exists()) {
                    for (data in snapshot.children) {
                        val getData = data.getValue(ImagePost::class.java)
                        imagePostList.add(getData!!)
                        imagePostList.reverse()
                        _imagePostLiveData.value = imagePostList
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", error.toException())
            }
        }

        FBRef.imagePostRef.addValueEventListener(postListener)
    }

    fun createTextPostItem(newPostKey: String, textPost: TextPost) {
        FBRef.postRef
            .child(FBAuth.getUid()).child(newPostKey).setValue(textPost)
        textPostList.add(textPost)

        _textPostLiveData.value = textPostList
    }

    fun createImagePostItem(newPostKey: String, imagePost: ImagePost) {
        FBRef.imagePostRef
            .child(newPostKey).setValue(imagePost)
        imagePostList.add(imagePost)
        _imagePostLiveData.value = imagePostList
    }

    fun getUserImgUri(uid: String): String {
        var imgUri = ""
        if (uid != "") {
            FBRef.userRef.child(uid).child("imgUri").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    imgUri = dataSnapshot.value.toString()
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }
        return imgUri
    }

    fun getUserDisplayName(uid: String): String {
        var displayName = ""
        if (uid != "") {
            FBRef.userRef.child(uid).child("displayName").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    displayName = dataSnapshot.value.toString()
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }
        return displayName
    }


    fun deleteTextPostItem(position: Int) {

    }

    fun deleteImagePostItem(position: Int) {

    }

    fun deleteUser(uid: String) {

    }
}
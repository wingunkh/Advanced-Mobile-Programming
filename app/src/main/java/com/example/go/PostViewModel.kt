package com.example.go

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.go.Model.*
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

    private val userList = mutableListOf<UserModel>()
    private val _userLiveData = MutableLiveData<List<UserModel>>()
    val userLiveData: LiveData<List<UserModel>> get() = _userLiveData

    private val favoritePostList = mutableListOf<ImagePost>()

    init {
        initTextPostList()
        initImagePostList()
        initUserList()
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

    private fun initUserList() {
        val userListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                if(snapshot.exists()) {
                    for (data in snapshot.children) {
                        val getData = data.getValue(UserModel::class.java)
                        userList.add(getData!!)
                        userList.reverse()
                        _userLiveData.value = userList
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "loadUser:onCancelled", error.toException())
            }
        }

        FBRef.userRef.addValueEventListener(userListener)
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

    fun getUser(uid: String): UserModel {
        for(user in userList) {
            if(user.uid == uid)
                return user
        }
        return userList[1]
    }

    fun deleteTextPostItem(position: Int) {

    }

    fun deleteImagePostItem(position: Int) {

    }

    fun deleteUser(uid: String) {

    }
}
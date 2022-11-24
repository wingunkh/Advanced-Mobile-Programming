package com.example.go

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.go.Model.ImagePost
import com.example.go.Model.TextPost
import com.example.go.Model.UserModel
import com.example.go.Utils.FBAuth.getTime
import com.example.go.Utils.FBAuth.getDisplayName
import com.example.go.Utils.FBRef

class PostViewModel : ViewModel() {

    private val textPostList = mutableListOf<TextPost>()
    private val _textPostLiveData = MutableLiveData<List<TextPost>>()
    val textPostLiveData: LiveData<List<TextPost>> get() = _textPostLiveData

    private val imagePostList = mutableListOf<ImagePost>()
    private val _imagePostLiveData = MutableLiveData<List<ImagePost>>()
    val imagePostLiveData: LiveData<List<ImagePost>> get() = _imagePostLiveData

    init {
        createTextPostDummyData()
        createImagePostDummyData()

        /* TODO Firebase 연동 후 DB 데이터 여기서 호출하면 됨 */
        val postWriter = getDisplayName()

        // initTextPostList()
    }

    private fun createTextPostDummyData() {
        textPostList.apply {
            add(TextPost("hi1", "geonhee", "hello", getTime()))
            add(TextPost("hi2", "geonhee", "hello", getTime()))
            add(TextPost("hi3", "geonhee", "hello", getTime()))
            add(TextPost("hi4", "geonhee", "hello", getTime()))
            add(TextPost("hi5", "geonhee", "hello", getTime()))
            add(TextPost("hi6", "geonhee", "hello", getTime()))
            add(TextPost("hi7", "geonhee", "hello", getTime()))
            add(TextPost("hi8", "geonhee", "hello", getTime()))
        }
        _textPostLiveData.value = textPostList
    }

    private fun createImagePostDummyData() {
        imagePostList.apply {
            add(ImagePost(R.drawable.cake, "hi1", "geonhee", "hello"))
            add(ImagePost(R.drawable.cake, "hi3", "geonhee", "hello"))
            add(ImagePost(R.drawable.cake, "hi4", "geonhee", "hello"))
            add(ImagePost(R.drawable.muhan, "hi5", "geonhee", "hello"))
            add(ImagePost(R.drawable.cake, "hi6", "geonhee", "hello"))
            add(ImagePost(R.drawable.muhan, "hi7", "geonhee", "hello"))
            add(ImagePost(R.drawable.cake, "hi8", "geonhee", "hello"))
        }
        _imagePostLiveData.value = imagePostList
    }

    fun initTextPostList() {
        /* TODO TextPost Data 호출 후 textPostList에 저장.*/


        _textPostLiveData.value = textPostList
    }

    fun initImagePostList() {
        /* TODO ImagePost Data 호출 후 imagePostList에 저장.*/


        _imagePostLiveData.value = imagePostList
    }

    fun createTextPostItem(textPost: TextPost) {
        FBRef.boardRef
            .child(textPost.user).setValue(textPost)
        textPostList.add(textPost)
        _textPostLiveData.value = textPostList
    }

    fun createImagePostItem(imagePost: ImagePost) {
        imagePostList.add(imagePost)
        _imagePostLiveData.value = imagePostList
    }
}
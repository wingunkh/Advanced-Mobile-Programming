package com.example.go

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.go.Model.ImagePost
import com.example.go.Model.TextPost

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
        // initTextPostList()
    }

    private fun createTextPostDummyData() {
        textPostList.apply {
            add(TextPost("hi1", "geonhee", "hello"))
            add(TextPost("hi2", "geonhee", "hello"))
            add(TextPost("hi3", "geonhee", "hello"))
            add(TextPost("hi4", "geonhee", "hello"))
            add(TextPost("hi5", "geonhee", "hello"))
            add(TextPost("hi6", "geonhee", "hello"))
            add(TextPost("hi7", "geonhee", "hello"))
            add(TextPost("hi8", "geonhee", "hello"))
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
        textPostList.add(textPost)
        _textPostLiveData.value = textPostList
    }

    fun createImagePostItem(imagePost: ImagePost) {
        imagePostList.add(imagePost)
        _imagePostLiveData.value = imagePostList
    }
}
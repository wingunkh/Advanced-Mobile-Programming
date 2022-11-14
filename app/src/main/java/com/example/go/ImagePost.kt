package com.example.go


import android.widget.ImageView
import java.io.Serializable

data class ImagePost(var imgSrc: Int, var title: String, var user: String, var content: String) : Serializable {

}
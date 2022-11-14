package com.example.go.ImagePost


import java.io.Serializable

data class ImagePost(var imgSrc: Int, var title: String, var user: String, var content: String) : Serializable {

}
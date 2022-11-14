package com.example.go.Post

import java.io.Serializable

data class Post(var title: String, var user: String, var content: String) : Serializable {

}
package com.example.go

import java.io.Serializable

data class Post(var title: String, var user: String, var content: String) : Serializable {

}
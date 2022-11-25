package com.example.go.Utils

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object FBRef {

        private val database = Firebase.database

        val postRef = database.getReference("post")

        val imagePostRef = database.getReference("imagePost")

        val userRef = database.getReference("User")

        val followingRef = database.getReference("following")
}
package com.example.go.Utils

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

object FBRef {

        private val database = Firebase.database

        val profileRef = database.getReference("profile")

        val followingRef = database.getReference("following")

        val boardRef = database.getReference("board")

        val commentRef = database.getReference("comment")

        val mapRef = database.getReference("Marker")

        val userRef = database.getReference("User")


}
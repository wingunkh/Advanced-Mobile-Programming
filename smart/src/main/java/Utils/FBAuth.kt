package com.example.go.Utils

import com.example.go.Utils.FBRef.userRef
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import java.text.SimpleDateFormat
import java.util.*

object FBAuth {

        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        fun getUid() : String{
            return auth.currentUser?.uid.toString()
        }

        fun getEmail() : String{
            return auth.currentUser?.email.toString()
        }

        fun getDisplayName() : String{
            return auth.currentUser?.displayName.toString()
        }

        fun getTime() : String{
            val currentDataTime = Calendar.getInstance().time
            val dataFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss",Locale.KOREA).format(currentDataTime)

            return dataFormat
        }

        fun setDisplayName() {
            userRef.child(getUid()).child("displayName").get().addOnSuccessListener{
                val name = it.value.toString()
                auth.currentUser?.updateProfile(userProfileChangeRequest {
                    displayName = name
                })
            }

        }

        fun setPassword(pwd:String) {
            auth.currentUser?.updatePassword(pwd)
        }
    }

package com.example.go

import android.app.Activity
import android.os.Bundle
import com.example.go.databinding.ActivitySmartBinding

class SmartActivity : Activity() {
    private val binding by lazy { ActivitySmartBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
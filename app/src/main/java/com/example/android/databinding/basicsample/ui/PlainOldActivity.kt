package com.example.android.databinding.basicsample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.databinding.basicsample.R

class PlainOldActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plain_activity)
    }
}
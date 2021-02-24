package com.patrykkosieradzki.eagleeye.utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.patrykkosieradzki.eagleeye.R

class ActivityForTestingFragment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity)
    }
}
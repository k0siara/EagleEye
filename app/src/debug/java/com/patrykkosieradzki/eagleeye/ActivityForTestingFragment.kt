package com.patrykkosieradzki.eagleeye

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ActivityForTestingFragment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity)
    }
}
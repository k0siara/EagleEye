package com.patrykkosieradzki.eagleeye.utils

import android.os.Bundle
import androidx.test.runner.AndroidJUnitRunner

class ScreenshotsRunner : AndroidJUnitRunner() {

    override fun onCreate(arguments: Bundle) {
        super.onCreate(arguments)
        Robot.shot = "true" == arguments.getString("shot", "false")
    }
}
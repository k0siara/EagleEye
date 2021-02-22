package com.patrykkosieradzki.eagleeye

import androidx.databinding.library.BuildConfig
import com.patrykkosieradzki.eagleeye.domain.AppConfiguration

class EagleEyeAppConfiguration : AppConfiguration {
    override val debug: Boolean = BuildConfig.DEBUG
    override val eagleEyeApiBaseUrl = "https://login.eagleeyenetworks.com/"
}
package com.patrykkosieradzki.eagleeye

import com.patrykkosieradzki.eagleeye.domain.AppConfiguration

class EagleEyeAppConfiguration : AppConfiguration {
    override val debug: Boolean = BuildConfig.DEBUG
    override val eagleEyeApiBaseUrl = "https://login.eagleeyenetworks.com/"
}
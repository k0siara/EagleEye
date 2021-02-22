package com.patrykkosieradzki.eagleeye.domain

interface AppConfiguration {
    val debug: Boolean
    val eagleEyeApiBaseUrl: String
}
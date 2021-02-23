package com.patrykkosieradzki.eagleeye.domain.model

data class CameraDetails(
    val id: String,
    val name: String,
    val guid: String,
    val tags: List<String>,
    val settings: CameraSettings
)

data class CameraSettings(
    val username: String,
    val password: String,
    val notes: String,
)
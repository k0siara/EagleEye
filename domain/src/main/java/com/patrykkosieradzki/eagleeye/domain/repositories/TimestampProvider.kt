package com.patrykkosieradzki.eagleeye.domain.repositories

interface TimestampProvider {
    val currentTimestamp: Long

    fun isExpired(timestamp: Long): Boolean
}

class TimestampProviderImpl : TimestampProvider {
    override val currentTimestamp = System.currentTimeMillis()
    override fun isExpired(timestamp: Long) = currentTimestamp > timestamp
}
package com.patrykkosieradzki.eagleeye.domain.repositories

private const val MILLIS_IN_SECOND = 1000L

interface TimestampProvider {
    val currentTimestamp: Long

    fun isExpired(timestamp: Long): Boolean
}

fun TimestampProvider.getTimestampInFuture(seconds: Int) =
    currentTimestamp + seconds * MILLIS_IN_SECOND

class TimestampProviderImpl : TimestampProvider {
    override val currentTimestamp = System.currentTimeMillis()
    override fun isExpired(timestamp: Long) = currentTimestamp > timestamp
}
package com.patrykkosieradzki.eagleeye.network.di

import com.patrykkosieradzki.eagleeye.domain.AppConfiguration
import com.patrykkosieradzki.eagleeye.network.utils.CookieReceiverInterceptor
import com.patrykkosieradzki.eagleeye.network.utils.CookieSupplierInterceptor
import mu.KotlinLogging
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

interface OkHttpClientFactory {
    fun createOkHttpClient(): OkHttpClient
}

class CustomOkHttpClientFactory(
    private val appConfiguration: AppConfiguration
) : OkHttpClientFactory {
    private val logger = KotlinLogging.logger("OkHttp")

    override fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(CookieReceiverInterceptor())
            addInterceptor(CookieSupplierInterceptor())

            if (appConfiguration.debug) {
                val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                    override fun log(message: String) {
                        logger.debug { message }
                    }
                }).apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                addInterceptor(loggingInterceptor)
            }
        }.build()
    }
}



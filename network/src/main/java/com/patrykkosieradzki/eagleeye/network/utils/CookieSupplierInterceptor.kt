package com.patrykkosieradzki.eagleeye.network.utils

import com.patrykkosieradzki.eagleeye.domain.usecases.GetSessionUseCase
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CookieSupplierInterceptor : Interceptor, KoinComponent {
    private val logger = KotlinLogging.logger("CookieSupplierInterceptor")

    private val getSessionUseCase: GetSessionUseCase by inject()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()

        runBlocking {
            val cookie = getSessionUseCase.invoke()
            cookie?.apply {
                logger.debug { "Cookie valid, applying to the request" }
                requestBuilder.addHeader(COOKIE_HEADER, "$COOKIE_KEY=$cookie")
            }
        }

        return chain.proceed(requestBuilder.build())
    }

    companion object {
        const val COOKIE_HEADER = "Cookie"
        const val COOKIE_KEY = "auth_key"
    }
}
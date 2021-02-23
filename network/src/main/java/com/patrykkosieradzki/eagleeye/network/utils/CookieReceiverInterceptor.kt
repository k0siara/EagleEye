package com.patrykkosieradzki.eagleeye.network.utils

import com.patrykkosieradzki.eagleeye.domain.repositories.SessionState
import com.patrykkosieradzki.eagleeye.domain.usecases.SaveSessionUseCase
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class CookieReceiverInterceptor : Interceptor, KoinComponent {
    private val logger = KotlinLogging.logger("CookieReceiverInterceptor")

    private val saveSessionUseCase: SaveSessionUseCase by inject()

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (response.headers(COOKIE_HEADER).isNotEmpty()) {
            var authKeyCookie: String? = null
            var timestamp = 0L

            val cookies = response.headers(COOKIE_HEADER)
            cookies.forEach {
                if (it.contains(AUTH_KEY_COOKIE_KEY)) {
                    logger.debug { "Cookie found in response" }
                    val regexResults = COOKIE_REGEX.findAll(it)
                    regexResults.forEach {group ->
                        val key = group.groupValues[1]
                        val value = group.groupValues[2]
                        if (key == AUTH_KEY_COOKIE_KEY) {
                            authKeyCookie = value
                        } else if (key == EXPIRES_COOKIE_KEY) {
                            timestamp = Date(value).time
                        }
                    }
                }
            }

            runBlocking {
                saveSessionUseCase.invoke(SessionState(
                    authKeyCookie = authKeyCookie,
                    validUntilTimestamp = timestamp
                ))
            }
        }

        return response
    }

    companion object {
        const val COOKIE_HEADER = "Set-Cookie"
        const val AUTH_KEY_COOKIE_KEY = "auth_key"
        const val EXPIRES_COOKIE_KEY = "expires"
        val COOKIE_REGEX = "(\\w+)=([\\w~, +-:]+)".toRegex()
    }
}
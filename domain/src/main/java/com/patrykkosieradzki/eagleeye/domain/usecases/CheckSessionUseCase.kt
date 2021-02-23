package com.patrykkosieradzki.eagleeye.domain.usecases

import com.patrykkosieradzki.eagleeye.domain.exceptions.ApiException
import mu.KotlinLogging
import java.io.IOException

interface CheckSessionUseCase {
    fun invoke()
    fun isValid(): Boolean
}

class CheckSessionUseCaseImpl(
    private val getSessionUseCase: GetSessionUseCase,
    private val clearSessionUseCase: ClearSessionUseCase
) : CheckSessionUseCase {

    private val logger = KotlinLogging.logger("CheckSessionUseCaseImpl")

    override fun invoke() {
        try {
            if (getSessionUseCase.invoke() == null) {
                throw SessionTimeoutException()
            }
            logger.debug { "Session is valid" }
        } catch (e: SessionTimeoutException) {
            logger.warn ("Session timeout - clearing session", e)
            clearSessionUseCase.invoke()
            throw ApiException.ServiceSessionClosedException()
        }
    }

    override fun isValid(): Boolean = try {
        invoke()
        true
    } catch (ex: ApiException.ServiceSessionClosedException) {
        false
    }
}

class SessionTimeoutException : IOException()
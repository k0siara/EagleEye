package com.patrykkosieradzki.eagleeye.network.utils

import com.patrykkosieradzki.eagleeye.domain.AppConfiguration
import com.patrykkosieradzki.eagleeye.domain.exceptions.ApiException
import com.patrykkosieradzki.eagleeye.domain.usecases.ClearSessionUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

const val GENERAL_ERROR_MESSAGE = "Service not available, try again later"

class NetworkHandler(
    private val clearSessionUseCase: ClearSessionUseCase,
    private val appConfiguration: AppConfiguration
) {

    @Suppress("ThrowsCount", "TooGenericExceptionCaught")
    suspend fun <T> safeNetworkCall(
        errorMapper: (Throwable) -> Throwable = { it },
        block: suspend CoroutineScope.() -> ApiResult<T>
    ): T =
        coroutineScope {
            try {
                when (val apiResult: ApiResult<T> = block()) {
                    is ApiResult.Success -> apiResult.data!!
                    is ApiResult.Failure -> throw mapApiErrorToApiException(apiResult.apiError)
                    is ApiResult.NetworkError -> throw ApiException.NetworkError(
                        hideErrorMessageInNonDebugBuilds(apiResult)
                    )
                    is ApiResult.OtherError -> throw ApiException.OtherError(
                        hideErrorMessageInNonDebugBuilds(apiResult)
                    )
                    ApiResult.SessionTimeoutError -> {
                        clearSessionUseCase.invoke()
                        throw ApiException.ServiceSessionClosedException()
                    }
                }
            } catch (throwable: Throwable) {
                throw errorMapper(throwable)
            }
        }

    private fun hideErrorMessageInNonDebugBuilds(apiResult: ApiResult.NetworkError): String {
        return if (appConfiguration.debug) apiResult.error else GENERAL_ERROR_MESSAGE
    }

    private fun hideErrorMessageInNonDebugBuilds(apiResult: ApiResult.OtherError): String {
        return if (appConfiguration.debug) apiResult.error else GENERAL_ERROR_MESSAGE
    }

    private fun mapApiErrorToApiException(apiError: String): ApiException {
        val apiException = ApiException.UnknownApiException(apiError)
        if (apiException.shouldLogout) {
            clearSessionUseCase.invoke()
        }
        return apiException
    }
}
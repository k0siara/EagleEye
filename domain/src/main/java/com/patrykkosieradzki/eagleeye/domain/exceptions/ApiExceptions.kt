package com.patrykkosieradzki.eagleeye.domain.exceptions

sealed class ApiException(
    val errorMessage: String,
    val shouldLogout: Boolean
) : RuntimeException(errorMessage) {
    class ServiceSessionClosedException(
        errorMessage: String = "Session has expired. Login again to the App."
    ) : ApiException(
        errorMessage,
        true
    )

    class UnknownApiException(
        errorMessage: String
    ) : ApiException(
        errorMessage,
        false
    )

    class NetworkError(errorMessage: String) : ApiException(errorMessage, false)

    class OtherError(errorMessage: String) : ApiException(errorMessage, false)
}
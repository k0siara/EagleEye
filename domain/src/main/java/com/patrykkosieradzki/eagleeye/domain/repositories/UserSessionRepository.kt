package com.patrykkosieradzki.eagleeye.domain.repositories

data class SessionState(
    val authKeyCookie: String? = null,
    val validUntilTimestamp : Long = 0L
)

interface UserSessionRepository {
    val isLoggedIn: Boolean
    fun saveSessionState(sessionState: SessionState)
    fun getSessionState(): SessionState
    fun clearSessionState()
}

class InMemoryUserSessionRepository : UserSessionRepository {

    private var userSessionState = SessionState()

    override val isLoggedIn: Boolean
        get() = getSessionState().authKeyCookie != null

    @Synchronized
    override fun saveSessionState(sessionState: SessionState) {
        userSessionState = userSessionState.copy(
            authKeyCookie = sessionState.authKeyCookie,
            validUntilTimestamp = sessionState.validUntilTimestamp
        )
    }

    @Synchronized
    override fun getSessionState() = userSessionState

    @Synchronized
    override fun clearSessionState() {
        userSessionState = userSessionState.copy(
            authKeyCookie = null,
            validUntilTimestamp = 0L
        )
    }
}
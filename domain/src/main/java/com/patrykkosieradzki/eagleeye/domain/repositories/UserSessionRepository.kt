package com.patrykkosieradzki.eagleeye.domain.repositories

data class SessionState(
    val token: String? = null,
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
        get() = getSessionState().token != null

    @Synchronized
    override fun saveSessionState(sessionState: SessionState) {
        userSessionState = userSessionState.copy(
            token = sessionState.token,
            validUntilTimestamp = sessionState.validUntilTimestamp
        )
    }

    @Synchronized
    override fun getSessionState() = userSessionState

    @Synchronized
    override fun clearSessionState() {
        userSessionState = userSessionState.copy(
            token = null,
            validUntilTimestamp = 0L
        )
    }
}
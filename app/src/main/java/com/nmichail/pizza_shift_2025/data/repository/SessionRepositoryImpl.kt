package com.nmichail.pizza_shift_2025.data.repository

import android.content.SharedPreferences
import com.nmichail.pizza_shift_2025.data.remote.AuthApi
import com.nmichail.pizza_shift_2025.domain.repository.SessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val prefs: SharedPreferences,
    private val api: AuthApi
) : SessionRepository {
    companion object {
        private const val KEY_TOKEN = "token"
    }

    override suspend fun isAuthorized(): Boolean = withContext(Dispatchers.IO) {
        val token = getToken() ?: return@withContext false
        try {
            val originalApi = api
            val sessionResponse = originalApi.getSessionWithToken(token)
            sessionResponse.success
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun setToken(token: String?) {
        prefs.edit().apply {
            if (token != null) putString(KEY_TOKEN, token)
            else remove(KEY_TOKEN)
        }.apply()
    }

    override suspend fun getToken(): String? = prefs.getString(KEY_TOKEN, null)
}
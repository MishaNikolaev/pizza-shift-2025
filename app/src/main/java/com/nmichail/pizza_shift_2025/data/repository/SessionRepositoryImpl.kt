package com.nmichail.pizza_shift_2025.data.repository

import android.content.SharedPreferences
import com.nmichail.pizza_shift_2025.data.remote.AuthApi
import com.nmichail.pizza_shift_2025.domain.repository.SessionRepository
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val prefs: SharedPreferences,
    private val api: AuthApi
) : SessionRepository {
    companion object {
        private const val KEY_TOKEN = "token"
    }

    override suspend fun isAuthorized(): Boolean {
        val token = getToken() ?: return false
        return try {
            val sessionResponse = api.getSession("Bearer $token")
            sessionResponse.success
        } catch (e: Exception) {
            false
        }
    }

    override fun setToken(token: String?) {
        prefs.edit().apply {
            if (token != null) putString(KEY_TOKEN, token)
            else remove(KEY_TOKEN)
        }.apply()
    }

    override fun getToken(): String? {
        val token = prefs.getString(KEY_TOKEN, null)
        return token
    }
}
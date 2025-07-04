package com.nmichail.pizza_shift_2025.data.repository

import android.content.SharedPreferences
import com.nmichail.pizza_shift_2025.data.remote.AuthApi
import com.nmichail.pizza_shift_2025.domain.repository.SessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import android.util.Log

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
            val sessionResponse = api.getSession("Bearer $token")
            //Log.d("SessionRepository", "isAuthorized: server response = ${sessionResponse.success}, reason = ${sessionResponse.reason}")
            sessionResponse.success
        } catch (e: Exception) {
            //Log.d("SessionRepository", "isAuthorized: exception = ${e.message}")
            false
        }
    }

    override suspend fun setToken(token: String?) {
        //Log.d("SessionRepository", "setToken: $token")
        prefs.edit().apply {
            if (token != null) putString(KEY_TOKEN, token)
            else remove(KEY_TOKEN)
        }.apply()
    }

    override suspend fun getToken(): String? {
        val token = prefs.getString(KEY_TOKEN, null)
        //Log.d("SessionRepository", "getToken: $token")
        return token
    }
}
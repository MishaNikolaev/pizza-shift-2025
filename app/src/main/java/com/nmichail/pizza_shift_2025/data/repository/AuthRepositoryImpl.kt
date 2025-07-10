package com.nmichail.pizza_shift_2025.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nmichail.pizza_shift_2025.data.dto.OtpRequestDto
import com.nmichail.pizza_shift_2025.data.dto.SignInRequestDto
import com.nmichail.pizza_shift_2025.data.remote.AuthApi
import com.nmichail.pizza_shift_2025.dataStore
import com.nmichail.pizza_shift_2025.domain.repository.AuthRepository
import com.nmichail.pizza_shift_2025.domain.repository.SessionRepository
import com.nmichail.pizza_shift_2025.presentation.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val sessionRepository: SessionRepository,
    private val context: Context
) : AuthRepository {

    override suspend fun getOtp(phone: String): Result<Unit> = try {
        val response = api.requestOtp(OtpRequestDto(phone))
        if (response.success) Result.Success(Unit)
        else Result.Error(response.reason ?: "Unknown error")
    } catch (e: Exception) {
        Result.Error(e.message ?: "Unknown error")
    }

    override suspend fun getSignIn(phone: String, code: String): Result<Unit> = try {
        val response = api.signIn(SignInRequestDto(phone, code))
        if (response.success && response.token != null) {
            sessionRepository.setToken(response.token)
            Result.Success(Unit)
        } else {
            val reason = response.reason ?: "Unknown error"
            val userMessage = if (reason.contains("400") || reason.contains("Bad Request")) {
                "Вы ввели неверный код. Попробуйте ещё раз."
            } else reason
            Result.Error(userMessage)
        }
    } catch (e: Exception) {
        val message = e.message ?: "Unknown error"
        val userMessage = if (message.contains("400") || message.contains("Bad Request")) {
            "Вы ввели неверный код. Попробуйте ещё раз."
        } else message
        Result.Error(userMessage)
    }

    companion object {
        private val PHONE_KEY = stringPreferencesKey("user_phone")
    }

    suspend fun savePhoneToDataStore(phone: String) {
        context.dataStore.edit { prefs ->
            prefs[PHONE_KEY] = phone
        }
    }

    suspend fun getPhoneFromDataStore(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[PHONE_KEY]
    }

    val phoneFlow: Flow<String?> = context.dataStore.data
        .map { it[PHONE_KEY] }
} 
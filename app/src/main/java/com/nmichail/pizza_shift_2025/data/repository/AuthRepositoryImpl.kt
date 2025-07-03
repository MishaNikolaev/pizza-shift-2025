package com.nmichail.pizza_shift_2025.data.repository

import com.nmichail.pizza_shift_2025.data.dto.OtpRequestDto
import com.nmichail.pizza_shift_2025.data.dto.SignInRequestDto
import com.nmichail.pizza_shift_2025.data.remote.AuthApi
import com.nmichail.pizza_shift_2025.domain.entities.Result
import com.nmichail.pizza_shift_2025.domain.repository.AuthRepository
import com.nmichail.pizza_shift_2025.domain.repository.SessionRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val sessionRepository: SessionRepository
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
        } else Result.Error(response.reason ?: "Unknown error")
    } catch (e: Exception) {
        Result.Error(e.message ?: "Unknown error")
    }
} 
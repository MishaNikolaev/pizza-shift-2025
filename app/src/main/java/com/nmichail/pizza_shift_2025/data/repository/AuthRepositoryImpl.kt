package com.nmichail.pizza_shift_2025.data.repository

import com.nmichail.pizza_shift_2025.data.dto.OtpRequestDto
import com.nmichail.pizza_shift_2025.data.dto.SignInRequestDto
import com.nmichail.pizza_shift_2025.data.remote.AuthApi
import com.nmichail.pizza_shift_2025.domain.models.AuthResult
import com.nmichail.pizza_shift_2025.domain.models.OtpResult
import com.nmichail.pizza_shift_2025.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val api: AuthApi) : AuthRepository {
    override suspend fun getOtp(phone: String): OtpResult = try {
        val response = api.requestOtp(OtpRequestDto(phone))
        OtpResult(isSuccess = response.success, error = response.reason)
    } catch (e: Exception) {
        OtpResult(isSuccess = false, error = e.message)
    }

    override suspend fun getSignIn(phone: String, code: String): AuthResult = try {
        val response = api.signIn(SignInRequestDto(phone, code))
        AuthResult(
            isSuccess = response.success,
            error = response.reason
        )
    } catch (e: Exception) {
        AuthResult(isSuccess = false, error = e.message)
    }
} 
package com.nmichail.pizza_shift_2025.domain.repository

import com.nmichail.pizza_shift_2025.domain.models.AuthResult
import com.nmichail.pizza_shift_2025.domain.models.OtpResult


interface AuthRepository {

    suspend fun getOtp(phone: String): OtpResult

    suspend fun getSignIn(phone: String, code: String): AuthResult
} 
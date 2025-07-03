package com.nmichail.pizza_shift_2025.domain.repository

import com.nmichail.pizza_shift_2025.domain.entities.Result

interface AuthRepository {

    suspend fun getOtp(phone: String): Result<Unit>

    suspend fun getSignIn(phone: String, code: String): Result<Unit>
} 
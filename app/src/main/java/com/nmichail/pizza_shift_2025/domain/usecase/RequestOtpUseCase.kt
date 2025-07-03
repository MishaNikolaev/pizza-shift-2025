package com.nmichail.pizza_shift_2025.domain.usecase

import com.nmichail.pizza_shift_2025.presentation.util.Result
import com.nmichail.pizza_shift_2025.domain.repository.AuthRepository
import javax.inject.Inject

class RequestOtpUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(phone: String): Result<Unit> = repository.getOtp(phone)
} 
package com.nmichail.pizza_shift_2025.domain.usecase

import com.nmichail.pizza_shift_2025.domain.models.OtpResult
import com.nmichail.pizza_shift_2025.domain.repository.AuthRepository
import javax.inject.Inject

class GetOtpUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(phone: String): OtpResult = repository.getOtp(phone)
} 
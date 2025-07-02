package com.nmichail.pizza_shift_2025.domain.usecase

import com.nmichail.pizza_shift_2025.domain.models.AuthResult
import com.nmichail.pizza_shift_2025.domain.repository.AuthRepository
import javax.inject.Inject

class GetSignInUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(phone: String, code: String): AuthResult = repository.getSignIn(phone, code)
} 
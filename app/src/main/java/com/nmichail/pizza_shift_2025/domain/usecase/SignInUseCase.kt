package com.nmichail.pizza_shift_2025.domain.usecase

import com.nmichail.pizza_shift_2025.domain.repository.AuthRepository
import com.nmichail.pizza_shift_2025.presentation.util.Result
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(phone: String, code: String): Result<Unit> = repository.getSignIn(phone, code)
} 
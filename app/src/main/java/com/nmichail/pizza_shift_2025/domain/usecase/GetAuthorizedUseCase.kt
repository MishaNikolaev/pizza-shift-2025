package com.nmichail.pizza_shift_2025.domain.usecase

import com.nmichail.pizza_shift_2025.domain.repository.SessionRepository
import javax.inject.Inject

class GetAuthorizedUseCase @Inject constructor(private val repository: SessionRepository) {
    suspend operator fun invoke(): Boolean = repository.isAuthorized()
} 
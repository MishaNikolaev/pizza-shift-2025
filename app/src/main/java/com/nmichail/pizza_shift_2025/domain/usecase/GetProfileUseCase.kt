package com.nmichail.pizza_shift_2025.domain.usecase

import com.nmichail.pizza_shift_2025.data.dto.UserDto
import com.nmichail.pizza_shift_2025.domain.repository.ProfileRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(token: String): UserDto? = repository.getProfile(token)
} 
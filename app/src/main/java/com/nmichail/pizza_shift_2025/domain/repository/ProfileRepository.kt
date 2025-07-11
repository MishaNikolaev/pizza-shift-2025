package com.nmichail.pizza_shift_2025.domain.repository

import com.nmichail.pizza_shift_2025.data.dto.UserDto

interface ProfileRepository {
    suspend fun getProfile(token: String): UserDto?
    suspend fun updateProfile(token: String, user: UserDto): Boolean
} 
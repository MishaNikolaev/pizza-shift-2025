package com.nmichail.pizza_shift_2025.data.remote

import com.nmichail.pizza_shift_2025.data.dto.SessionResponse
import com.nmichail.pizza_shift_2025.data.dto.UpdateProfileRequestDto
import com.nmichail.pizza_shift_2025.data.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH

interface ProfileApi {
    @PATCH("users/profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body request: UpdateProfileRequestDto
    ): UserDto
} 
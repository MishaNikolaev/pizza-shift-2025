package com.nmichail.pizza_shift_2025.data.dto

data class SessionResponse(
    val success: Boolean,
    val reason: String?,
    val user: UserDto?
)

package com.nmichail.pizza_shift_2025.domain.models

data class AuthResult(
    val isSuccess: Boolean,
    val error: String? = null
) 
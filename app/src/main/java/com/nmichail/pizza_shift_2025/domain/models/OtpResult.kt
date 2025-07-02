package com.nmichail.pizza_shift_2025.domain.models

data class OtpResult(
    val isSuccess: Boolean,
    val error: String? = null
) 
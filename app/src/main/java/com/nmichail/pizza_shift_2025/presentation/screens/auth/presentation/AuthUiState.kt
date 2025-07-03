package com.nmichail.pizza_shift_2025.presentation.screens.auth.presentation

sealed class AuthUiState {
    object Loading : AuthUiState()

    data class EnterPhone(
        val phone: String = "",
        val error: String? = null
    ) : AuthUiState()

    data class EnterCode(
        val phone: String,
        val code: String = "",
        val secondsLeft: Int = 0,
        val error: String? = null
    ) : AuthUiState()
} 
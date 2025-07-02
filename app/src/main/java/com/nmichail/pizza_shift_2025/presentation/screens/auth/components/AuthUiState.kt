package com.nmichail.pizza_shift_2025.presentation.screens.auth.components

import com.nmichail.pizza_shift_2025.presentation.screens.auth.AuthScreenState


data class AuthUiState(
    val screenState: AuthScreenState = AuthScreenState.EnterPhone,
    val phone: String = "",
    val code: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
) 
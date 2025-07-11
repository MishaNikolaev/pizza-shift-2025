package com.nmichail.pizza_shift_2025.presentation.screens.profile.presentation

import com.nmichail.pizza_shift_2025.data.dto.UserDto

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class View(val user: UserDto) : ProfileUiState()
    data class Edit(val user: UserDto) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}
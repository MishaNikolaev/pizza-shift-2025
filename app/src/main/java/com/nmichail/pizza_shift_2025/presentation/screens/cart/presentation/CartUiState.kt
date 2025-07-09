package com.nmichail.pizza_shift_2025.presentation.screens.cart.presentation

import com.nmichail.pizza_shift_2025.domain.entities.CartItem

sealed interface CartUiState {
    object Loading : CartUiState
    data class Content(
        val items: List<CartItem> = emptyList(),
        val totalPrice: Int = 0
    ) : CartUiState
} 
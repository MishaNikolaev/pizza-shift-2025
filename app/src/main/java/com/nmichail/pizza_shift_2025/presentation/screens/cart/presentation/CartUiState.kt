package com.nmichail.pizza_shift_2025.presentation.screens.cart.presentation

import com.nmichail.pizza_shift_2025.domain.entities.CartItem

data class CartUiState(

    val items: List<CartItem> = emptyList(),

    val totalPrice: Int = 0

) 
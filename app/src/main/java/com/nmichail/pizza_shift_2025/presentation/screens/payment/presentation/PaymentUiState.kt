package com.nmichail.pizza_shift_2025.presentation.screens.payment.presentation

import com.nmichail.pizza_shift_2025.data.dto.PizzaOrderDto

data class PaymentUiState(
    val lastname: String = "",
    val firstname: String = "",
    val phone: String = "",
    val email: String = "",
    val city: String = "",
    val street: String = "",
    val house: String = "",
    val apartment: String = "",
    val comment: String = "",
    val lastOrder: PizzaOrderDto? = null,
    val payError: String? = null,
    val paySuccess: Boolean = false
)
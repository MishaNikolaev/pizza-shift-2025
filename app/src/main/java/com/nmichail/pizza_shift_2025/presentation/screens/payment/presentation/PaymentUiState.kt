package com.nmichail.pizza_shift_2025.presentation.screens.payment.presentation

data class PaymentUiState(
    val lastname: String = "",
    val firstname: String = "",
    val phone: String = "",
    val email: String = "",
    val city: String = "",
    val street: String = "",
    val house: String = "",
    val apartment: String = "",
    val comment: String = ""
)
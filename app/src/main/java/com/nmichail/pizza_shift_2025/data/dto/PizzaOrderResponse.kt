package com.nmichail.pizza_shift_2025.data.dto

data class PizzaOrderResponse(
    val success: Boolean,
    val reason: String?,
    val order: PizzaOrderDto
)
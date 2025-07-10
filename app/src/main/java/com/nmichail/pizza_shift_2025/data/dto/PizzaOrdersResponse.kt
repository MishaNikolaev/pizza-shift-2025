package com.nmichail.pizza_shift_2025.data.dto

data class PizzaOrdersResponse(
    val success: Boolean,
    val reason: String?,
    val orders: List<PizzaOrderDto>
)
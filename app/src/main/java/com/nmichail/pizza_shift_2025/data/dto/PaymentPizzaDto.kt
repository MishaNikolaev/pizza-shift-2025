package com.nmichail.pizza_shift_2025.data.dto

data class PaymentPizzaDto(
    val id: String,
    val toppings: List<String>,
    val size: String,
    val dough: String
)
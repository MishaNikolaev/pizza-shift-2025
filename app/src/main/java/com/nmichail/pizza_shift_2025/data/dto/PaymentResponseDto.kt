package com.nmichail.pizza_shift_2025.data.dto

import com.google.gson.annotations.SerializedName

data class PaymentResponseDto(
    val success: Boolean,
    val reason: String?,
    val order: PizzaOrderDto
)
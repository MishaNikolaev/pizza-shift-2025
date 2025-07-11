package com.nmichail.pizza_shift_2025.data.dto

data class CreatePaymentDebitCardDto(
    val pan: String,
    val expireDate: String,
    val cvv: String
)
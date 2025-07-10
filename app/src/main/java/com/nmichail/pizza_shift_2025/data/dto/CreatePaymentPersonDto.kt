package com.nmichail.pizza_shift_2025.data.dto

data class CreatePaymentPersonDto(
    val firstname: String,
    val lastname: String,
    val middlename: String?,
    val phone: String
)

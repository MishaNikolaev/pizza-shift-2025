package com.nmichail.pizza_shift_2025.data.dto

data class CreateDeliveryOrderSenderAddressDto(
    val street: String,
    val house: String,
    val apartment: String,
    val comment: String?
)
package com.nmichail.pizza_shift_2025.data.dto

import com.google.gson.annotations.SerializedName

data class PizzaOrderDto(
    @SerializedName("_id")
    val id: String?,
    val pizzas: List<PizzaOrderItemDto>,
    val totalPrice: Int,
    val person: CreatePaymentPersonDto,
    val receiverAddress: CreateDeliveryOrderSenderAddressDto,
    val status: Int,
    val cancellable: Boolean
)
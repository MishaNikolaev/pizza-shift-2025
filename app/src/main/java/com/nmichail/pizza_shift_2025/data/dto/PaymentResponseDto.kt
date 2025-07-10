package com.nmichail.pizza_shift_2025.data.dto

import com.google.gson.annotations.SerializedName

data class PaymentResponseDto(
    val success: Boolean,
    val reason: String?,
    val order: PizzaOrderDto
)

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

data class PizzaOrderItemDto(
    val id: String,
    val name: String,
    val ingredients: List<PizzaIngredientDto>,
    val toppings: List<PizzaIngredientDto>,
    val description: String,
    val sizes: List<PizzaSizeDto>,
    val doughs: List<PizzaDoughDto>,
    val calories: Int,
    val protein: String,
    val totalFat: String
)

data class PizzaIngredientDto(
    val type: String,
    val price: Int,
    val img: String
)

data class PizzaSizeDto(
    val type: String,
    val price: Int
)

data class PizzaDoughDto(
    val type: String,
    val price: Int
) 
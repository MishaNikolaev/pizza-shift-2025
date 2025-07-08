package com.nmichail.pizza_shift_2025.data.dto

data class PaymentRequestDto(
    val receiverAddress: CreateDeliveryOrderSenderAddressDto,
    val person: CreatePaymentPersonDto,
    val debitCard: CreatePaymentDebitCardDto,
    val pizzas: List<PaymentPizzaDto>
)

data class CreateDeliveryOrderSenderAddressDto(
    val street: String,
    val house: String,
    val apartment: String,
    val comment: String?
)

data class CreatePaymentPersonDto(
    val firstname: String,
    val lastname: String,
    val middlename: String?,
    val phone: String
)

data class CreatePaymentDebitCardDto(
    val pan: String,
    val expireDate: String,
    val cvv: String
)

data class PaymentPizzaDto(
    val id: String,
    val toppings: List<String>,
    val size: String,
    val dough: String
) 
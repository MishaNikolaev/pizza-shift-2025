package com.nmichail.pizza_shift_2025.data.dto

data class PaymentRequestDto(
    val receiverAddress: CreateDeliveryOrderSenderAddressDto,
    val person: CreatePaymentPersonDto,
    val debitCard: CreatePaymentDebitCardDto,
    val pizzas: List<PaymentPizzaDto>
)
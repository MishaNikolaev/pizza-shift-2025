package com.nmichail.pizza_shift_2025.data.repository

import com.nmichail.pizza_shift_2025.data.dto.PaymentRequestDto
import com.nmichail.pizza_shift_2025.data.dto.PaymentResponseDto
import com.nmichail.pizza_shift_2025.data.remote.PizzaApi
import com.nmichail.pizza_shift_2025.domain.repository.PaymentRepository
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val api: PizzaApi
) : PaymentRepository {
    override suspend fun payForOrder(request: PaymentRequestDto): PaymentResponseDto {
        return api.payForOrder(request)
    }
} 
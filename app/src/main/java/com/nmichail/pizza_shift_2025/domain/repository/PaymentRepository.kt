package com.nmichail.pizza_shift_2025.domain.repository

import com.nmichail.pizza_shift_2025.data.dto.PaymentRequestDto
import com.nmichail.pizza_shift_2025.data.dto.PaymentResponseDto

interface PaymentRepository {

    suspend fun payForOrder(request: PaymentRequestDto): PaymentResponseDto
    
} 
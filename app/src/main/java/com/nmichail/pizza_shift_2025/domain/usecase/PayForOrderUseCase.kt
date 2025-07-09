package com.nmichail.pizza_shift_2025.domain.usecase

import com.nmichail.pizza_shift_2025.data.dto.PaymentRequestDto
import com.nmichail.pizza_shift_2025.data.dto.PaymentResponseDto
import com.nmichail.pizza_shift_2025.domain.repository.PaymentRepository
import javax.inject.Inject

class PayForOrderUseCase @Inject constructor(private val repository: PaymentRepository) {
    suspend operator fun invoke(request: PaymentRequestDto): PaymentResponseDto = repository.payForOrder(request)
} 
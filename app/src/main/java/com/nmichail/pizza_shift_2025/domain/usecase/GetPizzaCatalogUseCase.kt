package com.nmichail.pizza_shift_2025.domain.usecase

import com.nmichail.pizza_shift_2025.domain.repository.PizzaRepository
import javax.inject.Inject

class GetPizzaCatalogUseCase @Inject constructor(private val repository: PizzaRepository) {
    suspend operator fun invoke() = repository.getCatalog()
} 
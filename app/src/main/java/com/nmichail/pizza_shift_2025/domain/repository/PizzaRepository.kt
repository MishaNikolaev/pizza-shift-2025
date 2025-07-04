package com.nmichail.pizza_shift_2025.domain.repository

import com.nmichail.pizza_shift_2025.domain.entities.Pizza

interface PizzaRepository {

    suspend fun getCatalog(): List<Pizza>

} 
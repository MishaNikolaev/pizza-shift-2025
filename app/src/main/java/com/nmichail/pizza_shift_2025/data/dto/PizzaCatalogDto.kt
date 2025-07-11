package com.nmichail.pizza_shift_2025.data.dto

data class PizzaCatalogDto(
    val success: Boolean,
    val reason: String?,
    val catalog: List<PizzaDto>
)
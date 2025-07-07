package com.nmichail.pizza_shift_2025.domain.entities

data class Pizza(
    val id: String,
    val name: String,
    val description: String?,
    val imageUrl: String?,
    val price: Int,
    val sizes: List<String> = emptyList(),
    val sizePrices: Map<String, Int> = emptyMap(),
    val toppings: List<PizzaTopping> = emptyList(),
    val doughs: List<String> = emptyList()
)
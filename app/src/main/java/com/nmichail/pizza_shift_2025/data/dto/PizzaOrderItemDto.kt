package com.nmichail.pizza_shift_2025.data.dto

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
package com.nmichail.pizza_shift_2025.data.dto


data class PizzaDto(
    val id: String,
    val name: String,
    val description: String?,
    val ingredients: List<IngredientDto>,
    val toppings: List<ToppingDto>,
    val sizes: List<SizeDto>,
    val doughs: List<DoughDto>,
    val calories: Int?,
    val protein: String?,
    val totalFat: String?,
    val carbohydrates: String?,
    val sodium: String?,
    val allergens: List<String>?,
    val isVegetarian: Boolean?,
    val isGlutenFree: Boolean?,
    val isNew: Boolean?,
    val isHit: Boolean?,
    val img: String?
)
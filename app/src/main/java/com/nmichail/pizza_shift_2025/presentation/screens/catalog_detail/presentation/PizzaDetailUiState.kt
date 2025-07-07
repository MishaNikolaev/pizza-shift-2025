package com.nmichail.pizza_shift_2025.presentation.screens.catalog_detail.presentation

import com.nmichail.pizza_shift_2025.domain.entities.Pizza

data class PizzaDetailUiState(

    val pizza: Pizza? = null,

    val selectedSize: String? = null,

    val selectedToppings: Set<String> = emptySet(),
    
    val isAddedToCart: Boolean = false,

    val cartItemId: String? = null

)
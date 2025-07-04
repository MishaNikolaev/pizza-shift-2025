package com.nmichail.pizza_shift_2025.presentation.screens.catalog.presentation

import com.nmichail.pizza_shift_2025.domain.entities.Pizza

sealed class CatalogUiState {

    object Loading : CatalogUiState()

    data class Success(val pizzas: List<Pizza>) : CatalogUiState()

    data class Error(val message: String) : CatalogUiState()

}
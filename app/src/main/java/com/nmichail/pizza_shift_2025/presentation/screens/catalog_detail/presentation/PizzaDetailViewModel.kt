package com.nmichail.pizza_shift_2025.presentation.screens.catalog_detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmichail.pizza_shift_2025.domain.entities.Pizza
import com.nmichail.pizza_shift_2025.domain.repository.PizzaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PizzaDetailViewModel @Inject constructor(
    private val pizzaRepository: PizzaRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PizzaDetailUiState())
    val uiState: StateFlow<PizzaDetailUiState> = _uiState

    fun loadPizza(pizzaId: String) {
        viewModelScope.launch {
            val catalog = pizzaRepository.getCatalog()
            val pizza = catalog.find { it.id == pizzaId }
            if (pizza != null) {
                _uiState.value = PizzaDetailUiState(
                    pizza = pizza,
                    selectedSize = pizza.sizes.firstOrNull(),
                    selectedToppings = emptySet()
                )
            }
        }
    }

    fun selectSize(size: String) {
        _uiState.value = _uiState.value.copy(selectedSize = size)
    }

    fun toggleTopping(topping: String) {
        val current = _uiState.value
        val newSet = if (current.selectedToppings.contains(topping)) {
            current.selectedToppings - topping
        } else {
            current.selectedToppings + topping
        }
        _uiState.value = current.copy(selectedToppings = newSet)
    }
}
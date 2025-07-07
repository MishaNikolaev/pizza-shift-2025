package com.nmichail.pizza_shift_2025.presentation.screens.catalog_detail.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmichail.pizza_shift_2025.domain.entities.CartItem
import com.nmichail.pizza_shift_2025.domain.entities.Pizza
import com.nmichail.pizza_shift_2025.domain.repository.PizzaRepository
import com.nmichail.pizza_shift_2025.domain.usecase.AddToCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import kotlinx.coroutines.delay

@HiltViewModel
class PizzaDetailViewModel @Inject constructor(
    private val pizzaRepository: PizzaRepository,
    private val addToCartUseCase: AddToCartUseCase,
    private val cartRepository: com.nmichail.pizza_shift_2025.domain.repository.CartRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PizzaDetailUiState())
    val uiState: StateFlow<PizzaDetailUiState> = _uiState

    fun loadPizza(pizzaId: String, initialSize: String? = null, initialToppings: Set<String> = emptySet(), cartItemId: String? = null) {
        viewModelScope.launch {
            val catalog = pizzaRepository.getCatalog()
            val pizza = catalog.find { it.id == pizzaId }
            if (pizza != null) {
                _uiState.value = PizzaDetailUiState(
                    pizza = pizza,
                    selectedSize = initialSize ?: pizza.sizes.firstOrNull(),
                    selectedToppings = if (initialToppings.isNotEmpty()) initialToppings else emptySet(),
                    cartItemId = cartItemId
                )
            }
        }
    }

    fun selectSize(size: String) {
        val current = _uiState.value
        _uiState.value = current.copy(
            selectedSize = size,
            isAddedToCart = false 
        )
    }

    fun toggleTopping(topping: String) {
        val current = _uiState.value
        val newSet = if (current.selectedToppings.contains(topping)) {
            current.selectedToppings - topping
        } else {
            current.selectedToppings + topping
        }
        _uiState.value = current.copy(
            selectedToppings = newSet,
            isAddedToCart = false 
        )
    }

    fun addToCart(cartItemId: String? = null) {

        val currentState = _uiState.value
        val pizza = currentState.pizza
        val selectedSize = currentState.selectedSize

        if (pizza != null && selectedSize != null) {

            _uiState.value = currentState.copy(isAddedToCart = true)

            viewModelScope.launch {
                var count = 1
                if (cartItemId != null) {

                    val cartItems = cartRepository.getCartItems().first()
                    val oldItem = cartItems.find { it.id == cartItemId }
                    count = oldItem?.count ?: 1
                    cartRepository.removeFromCart(cartItemId)
                    var updatedCartItems: List<CartItem>

                    do {
                        delay(50)
                        updatedCartItems = cartRepository.getCartItems().first()
                    }
                    while (updatedCartItems.any { it.id == cartItemId })
                    val existing = updatedCartItems.find {
                        it.pizza.id == pizza.id &&
                        it.selectedSize == selectedSize &&
                        it.selectedToppings == currentState.selectedToppings
                    }
                    if (existing != null) {
                        cartRepository.updateCount(existing.id, existing.count + count)
                        return@launch
                    }
                }
                addToCartUseCase(
                    pizza = pizza,
                    selectedSize = selectedSize,
                    selectedToppings = currentState.selectedToppings
                )
            }
        }
    }
}
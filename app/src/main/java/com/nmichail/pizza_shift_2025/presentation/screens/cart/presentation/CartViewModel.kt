package com.nmichail.pizza_shift_2025.presentation.screens.cart.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmichail.pizza_shift_2025.data.dto.PizzaOrderDto
import com.nmichail.pizza_shift_2025.domain.entities.Pizza
import com.nmichail.pizza_shift_2025.domain.entities.PizzaTopping
import com.nmichail.pizza_shift_2025.domain.repository.CartRepository
import com.nmichail.pizza_shift_2025.domain.repository.PizzaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val pizzaRepository: PizzaRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()
    
    init {
        loadCartItems()
    }
    
    private fun loadCartItems() {
        viewModelScope.launch {
            cartRepository.getCartItems().collect { items ->
                _uiState.value = CartUiState.Content(
                    items = items,
                    totalPrice = items.sumOf { it.totalPrice }
                )
            }
        }
    }
    
    fun updateCount(cartItemId: String, count: Int) {
        cartRepository.updateCount(cartItemId, count)
    }
    
    fun removeItem(cartItemId: String) {
        cartRepository.removeFromCart(cartItemId)
    }

    fun repeatOrder(order: PizzaOrderDto) {
        Log.d("CartViewModel", "repeatOrder called for orderId=${order.id}")
        cartRepository.clearCart()
        Log.d("CartViewModel", "Cart cleared")
        val catalog = runBlocking { pizzaRepository.getCatalog() }
        order.pizzas.forEach { pizzaOrderItem ->
            val catalogPizza = catalog.find { it.id == pizzaOrderItem.id }
            val pizza = Pizza(
                id = pizzaOrderItem.id,
                name = pizzaOrderItem.name,
                description = pizzaOrderItem.description,
                imageUrl = catalogPizza?.imageUrl,
                price = pizzaOrderItem.sizes.firstOrNull()?.price ?: 0,
                sizes = pizzaOrderItem.sizes.map { it.type },
                sizePrices = pizzaOrderItem.sizes.associate { it.type to it.price },
                toppings = pizzaOrderItem.toppings.map { PizzaTopping(it.type, it.price, it.img) },
                doughs = pizzaOrderItem.doughs.map { it.type }
            )
            val selectedSize = pizzaOrderItem.sizes.firstOrNull()?.type ?: "medium"
            val selectedToppings = pizzaOrderItem.toppings.map { it.type }.toSet()
            val cartItem = com.nmichail.pizza_shift_2025.domain.entities.CartItem(
                id = java.util.UUID.randomUUID().toString(),
                pizza = pizza,
                selectedSize = selectedSize,
                selectedToppings = selectedToppings,
                count = 1
            )
            Log.d("CartViewModel", "Adding cartItem: $cartItem")
            cartRepository.addToCart(cartItem)
        }
        Log.d("CartViewModel", "repeatOrder finished")
    }
} 
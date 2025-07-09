package com.nmichail.pizza_shift_2025.presentation.screens.cart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmichail.pizza_shift_2025.domain.entities.CartItem
import com.nmichail.pizza_shift_2025.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
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
} 
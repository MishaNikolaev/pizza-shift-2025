package com.nmichail.pizza_shift_2025.presentation.screens.order_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmichail.pizza_shift_2025.data.dto.PizzaOrderDto
import com.nmichail.pizza_shift_2025.domain.repository.OrderRepository
import com.nmichail.pizza_shift_2025.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val repository: OrderRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {
    private val _order = MutableStateFlow<PizzaOrderDto?>(null)
    val order: StateFlow<PizzaOrderDto?> = _order.asStateFlow()

    fun loadOrder(orderId: String) {
        viewModelScope.launch {
            val token = sessionRepository.getToken()
            if (token != null) {
                try {
                    _order.value = repository.getOrder(orderId, "Bearer $token")
                } catch (e: Exception) {
                    _order.value = null
                }
            }
        }
    }
}
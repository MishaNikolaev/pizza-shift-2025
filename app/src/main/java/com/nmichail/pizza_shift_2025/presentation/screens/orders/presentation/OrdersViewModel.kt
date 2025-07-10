package com.nmichail.pizza_shift_2025.presentation.screens.orders

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
import android.util.Log

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val repository: OrderRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {
    private val _orders = MutableStateFlow<List<PizzaOrderDto>>(emptyList())
    val orders: StateFlow<List<PizzaOrderDto>> = _orders.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadOrders() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val token = sessionRepository.getToken()
                Log.d("TOKEN", "Token: $token")
                if (token != null) {
                    _orders.value = repository.getOrders("Bearer $token")
                    Log.d("OrdersViewModel", "orders loaded: " + _orders.value.joinToString { it.toString() })
                } else {
                    _error.value = "Не авторизован"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun cancelOrder(orderId: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val token = sessionRepository.getToken()
                if (token != null && !orderId.isNullOrBlank()) {
                    val result = repository.cancelOrder(orderId, "Bearer $token")
                    onResult(result)
                    if (result) loadOrders()
                } else {
                    onResult(false)
                    _error.value = "Не авторизован или неверный orderId"
                }
            } catch (e: retrofit2.HttpException) {
                Log.e("OrdersViewModel", "cancelOrder: HttpException ${e.code()} ${e.message()}")
                _error.value = "Ошибка отмены заказа: ${e.code()} ${e.message()}"
                onResult(false)
            } catch (e: Exception) {
                Log.e("OrdersViewModel", "cancelOrder: Exception ${e.message}")
                _error.value = "Неизвестная ошибка: ${e.message}"
                onResult(false)
            }
        }
    }
}
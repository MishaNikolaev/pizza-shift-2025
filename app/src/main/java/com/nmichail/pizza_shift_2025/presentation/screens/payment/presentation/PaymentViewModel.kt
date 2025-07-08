package com.nmichail.pizza_shift_2025.presentation.screens.payment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmichail.pizza_shift_2025.data.dto.*
import com.nmichail.pizza_shift_2025.domain.usecase.PayForOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val payForOrderUseCase: PayForOrderUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(PaymentUiState())
    val state: StateFlow<PaymentUiState> = _state

    fun onLastnameChanged(value: String) { _state.value = _state.value.copy(lastname = value) }

    fun onFirstnameChanged(value: String) { _state.value = _state.value.copy(firstname = value) }

    fun onEmailChanged(value: String) { _state.value = _state.value.copy(email = value) }
    
    fun onCityChanged(value: String) { _state.value = _state.value.copy(city = value) }

    fun submitOrder() {

    }
}
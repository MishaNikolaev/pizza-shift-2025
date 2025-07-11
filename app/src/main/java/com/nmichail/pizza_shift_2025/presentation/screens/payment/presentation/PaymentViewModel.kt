package com.nmichail.pizza_shift_2025.presentation.screens.payment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmichail.pizza_shift_2025.data.dto.*
import com.nmichail.pizza_shift_2025.domain.repository.CartRepository
import com.nmichail.pizza_shift_2025.domain.repository.PaymentRepository
import com.nmichail.pizza_shift_2025.domain.usecase.PayForOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val payForOrderUseCase: PayForOrderUseCase,
    private val paymentRepository: PaymentRepository,
    private val cartRepository: CartRepository
) : ViewModel() {
    private val _state = MutableStateFlow(PaymentUiState())
    val state: StateFlow<PaymentUiState> = _state

    fun handleLastnameChanged(value: String) { _state.value = _state.value.copy(lastname = value) }
    fun handleFirstnameChanged(value: String) { _state.value = _state.value.copy(firstname = value) }
    fun handleEmailChanged(value: String) { _state.value = _state.value.copy(email = value) }
    fun handleCityChanged(value: String) { _state.value = _state.value.copy(city = value) }
    fun handleStreetChanged(value: String) { _state.value = _state.value.copy(street = value) }
    fun handleHouseChanged(value: String) { _state.value = _state.value.copy(house = value) }
    fun handleApartmentChanged(value: String) { _state.value = _state.value.copy(apartment = value) }
    fun handleCommentChanged(value: String) { _state.value = _state.value.copy(comment = value) }
    fun handlePhoneChanged(value: String) { _state.value = _state.value.copy(phone = value) }

    fun pay(cardNumber: String, cardDate: String, cardCvv: String) {
        viewModelScope.launch {
            val state = state.value
            val lastname = state.lastname.trim()
            val firstname = state.firstname.trim()
            val city = state.city.trim()
            val email = state.email.trim()
            val phone = state.phone.trim()
            val street = state.street.trim()
            val house = state.house.trim()
            val apartment = state.apartment.trim()
            val comment = state.comment.trim().ifEmpty { null }
            val cardNumberDigits = cardNumber.filter { it.isDigit() }
            val cardDateDigits = cardDate.filter { it.isDigit() }
            val cardCvvDigits = cardCvv.filter { it.isDigit() }
            if (lastname.isEmpty() || firstname.isEmpty() || city.isEmpty() || email.isEmpty() || phone.isEmpty() || street.isEmpty() || house.isEmpty()) {
                _state.value = _state.value.copy(payError = "Заполните все поля")
                return@launch
            }
            if (cardNumberDigits.length != 16 || cardDateDigits.length != 4 || cardCvvDigits.length != 3) {
                _state.value = _state.value.copy(payError = "Заполните корректно данные карты")
                return@launch
            }
            val cartItems = cartRepository.getCartItems().first()
            if (cartItems.isEmpty()) {
                _state.value = _state.value.copy(payError = "Корзина пуста")
                return@launch
            }
            val pizzas = cartItems.map {
                PaymentPizzaDto(
                    id = it.pizza.id,
                    toppings = it.selectedToppings.toList(),
                    size = it.selectedSize,
                    dough = it.pizza.doughs.firstOrNull() ?: ""
                )
            }
            val request = PaymentRequestDto(
                receiverAddress = CreateDeliveryOrderSenderAddressDto(
                    street = street, house = house, apartment = apartment.ifEmpty { "-" }, comment = comment
                ),
                person = CreatePaymentPersonDto(
                    firstname = firstname,
                    lastname = lastname,
                    middlename = null,
                    phone = phone
                ),
                debitCard = CreatePaymentDebitCardDto(
                    pan = cardNumberDigits,
                    expireDate = cardDateDigits,
                    cvv = cardCvvDigits
                ),
                pizzas = pizzas
            )
            try {
                val response = payForOrderUseCase(request)
                if (response.success) {
                    cartRepository.clearCart()
                    _state.value = _state.value.copy(paySuccess = true, payError = null)
                } else {
                    _state.value = _state.value.copy(payError = response.reason ?: "Ошибка оплаты")
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(payError = e.message ?: "Ошибка оплаты")
            }
        }
    }

    fun loadLastOrder() {
        _state.value = _state.value.copy(lastOrder = paymentRepository.getLastOrder())
    }

    fun resetPaySuccess() {
        _state.value = _state.value.copy(paySuccess = false)
    }
}
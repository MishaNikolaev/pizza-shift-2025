package com.nmichail.pizza_shift_2025.presentation.screens.payment.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmichail.pizza_shift_2025.data.dto.*
import com.nmichail.pizza_shift_2025.domain.repository.CartRepository
import com.nmichail.pizza_shift_2025.domain.repository.PaymentRepository
import com.nmichail.pizza_shift_2025.domain.usecase.PayForOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _lastOrder = MutableStateFlow<PizzaOrderDto?>(null)
    val lastOrder = _lastOrder.asStateFlow()

    private val _payError = MutableStateFlow<String?>(null)
    val payError = _payError.asStateFlow()
    private val _paySuccess = MutableStateFlow(false)
    val paySuccess = _paySuccess.asStateFlow()

    fun onLastnameChanged(value: String) { _state.value = _state.value.copy(lastname = value) }

    fun onFirstnameChanged(value: String) { _state.value = _state.value.copy(firstname = value) }

    fun onEmailChanged(value: String) { _state.value = _state.value.copy(email = value) }

    fun onCityChanged(value: String) { _state.value = _state.value.copy(city = value) }
    fun onStreetChanged(value: String) { _state.value = _state.value.copy(street = value) }
    fun onHouseChanged(value: String) { _state.value = _state.value.copy(house = value) }
    fun onApartmentChanged(value: String) { _state.value = _state.value.copy(apartment = value) }
    fun onCommentChanged(value: String) { _state.value = _state.value.copy(comment = value) }

    fun onPhoneChanged(value: String) { _state.value = _state.value.copy(phone = value) }

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
            Log.d("PAYMENT", "pay() called with: lastname=$lastname, firstname=$firstname, city=$city, email=$email, phone=$phone, street=$street, house=$house, apartment=$apartment, comment=$comment, cardNumber=$cardNumberDigits, cardDate=$cardDateDigits, cardCvv=$cardCvvDigits")
            if (lastname.isEmpty() || firstname.isEmpty() || city.isEmpty() || email.isEmpty() || phone.isEmpty() || street.isEmpty() || house.isEmpty()) {
                Log.d("PAYMENT", "Validation failed: required fields")
                _payError.value = "Заполните все поля"
                return@launch
            }
            if (cardNumberDigits.length != 16 || cardDateDigits.length != 4 || cardCvvDigits.length != 3) {
                Log.d("PAYMENT", "Validation failed: card fields invalid")
                _payError.value = "Заполните корректно данные карты"
                return@launch
            }
            val cartItems = cartRepository.getCartItems().first()
            Log.d("PAYMENT", "cartItems: $cartItems")
            if (cartItems.isEmpty()) {
                Log.d("PAYMENT", "Validation failed: cart is empty")
                _payError.value = "Корзина пуста"
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
                Log.d("PAYMENT", "Payment response: $response")
                if (response.success) {
                    cartRepository.clearCart()
                    _paySuccess.value = true
                    _payError.value = null
                } else {
                    _payError.value = response.reason ?: "Ошибка оплаты"
                }
            } catch (e: Exception) {
                _payError.value = e.message ?: "Ошибка оплаты"
            }
        }
    }

    fun loadLastOrder() {
        _lastOrder.value = paymentRepository.getLastOrder()
    }

    fun resetPaySuccess() {
        _paySuccess.value = false
    }
}
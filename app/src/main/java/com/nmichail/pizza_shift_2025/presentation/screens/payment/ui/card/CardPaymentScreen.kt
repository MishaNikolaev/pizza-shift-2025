package com.nmichail.pizza_shift_2025.presentation.screens.payment.ui.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nmichail.pizza_shift_2025.presentation.screens.payment.presentation.PaymentViewModel

@Composable
fun CardPaymentScreen(
    onBack: (() -> Unit)? = null,
    onPay: (() -> Unit)? = null,
    viewModel: PaymentViewModel = hiltViewModel()
) {
    var cardNumberRaw by remember { mutableStateOf("") }
    val cardNumber = cardNumberRaw.chunked(4).joinToString(" ").take(19)
    var cardDateRaw by remember { mutableStateOf("") }
    val cardDate = when {
        cardDateRaw.isEmpty() -> ""
        cardDateRaw.length <= 2 -> cardDateRaw
        else -> cardDateRaw.take(2) + "/" + cardDateRaw.drop(2).take(2)
    }
    var cardCvv by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()
    var localError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(state.paySuccess) {
        if (state.paySuccess) {
            onPay?.invoke()
            viewModel.resetPaySuccess()
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        
        CardPaymentHeader(onBack = onBack)
        CardPaymentProgressBar()
        CardPaymentForm(
            cardNumber = cardNumber,
            cardDate = cardDate,
            cardCvv = cardCvv,
            onCardNumberChange = { 
                val digits = it.filter { ch -> ch.isDigit() }
                if (it.length < cardNumber.length) {
                    cardNumberRaw = digits.take(16)
                } else {
                    cardNumberRaw = digits.take(16)
                }
            },
            onCardDateChange = {
                val digits = it.filter { ch -> ch.isDigit() }
                if (it.length < cardDate.length) {
                    cardDateRaw = digits.take(4)
                } else {
                    cardDateRaw = digits.take(4)
                }
            },
            onCardCvvChange = {
                val digits = it.filter { ch -> ch.isDigit() }
                cardCvv = digits.take(3)
            }
        )
        
        CardPaymentButton(
            onPayClick = {
                localError = null
                viewModel.pay(
                    cardNumber = cardNumberRaw,
                    cardDate = cardDateRaw,
                    cardCvv = cardCvv
                )
            }
        )
        
        CardPaymentErrorMessage(error = state.payError)
    }
}
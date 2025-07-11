package com.nmichail.pizza_shift_2025.presentation.screens.payment.ui.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CardPaymentForm(
    cardNumber: String,
    cardDate: String,
    cardCvv: String,
    onCardNumberChange: (String) -> Unit,
    onCardDateChange: (String) -> Unit,
    onCardCvvChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            CardNumberField(
                value = cardNumber,
                onValueChange = onCardNumberChange
            )
            Spacer(modifier = Modifier.height(20.dp))
            CardDateAndCvvRow(
                cardDate = cardDate,
                cardCvv = cardCvv,
                onCardDateChange = onCardDateChange,
                onCardCvvChange = onCardCvvChange
            )
        }
    }
}
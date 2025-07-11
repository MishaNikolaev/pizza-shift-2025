package com.nmichail.pizza_shift_2025.presentation.screens.payment.ui.card

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CardPaymentErrorMessage(error: String?) {
    if (error != null) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(error, color = Color.Red)
    }
} 
package com.nmichail.pizza_shift_2025.presentation.screens.payment.ui.card

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nmichail.pizza_shift_2025.presentation.theme.OrangePizza

@Composable
fun CardPaymentButton(onPayClick: () -> Unit) {
    Spacer(modifier = Modifier.height(32.dp))
    Button(
        onClick = onPayClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = OrangePizza),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text("Оплатить", fontSize = 20.sp, color = Color.White)
    }
} 
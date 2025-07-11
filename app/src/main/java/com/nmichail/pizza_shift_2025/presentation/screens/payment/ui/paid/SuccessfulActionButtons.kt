package com.nmichail.pizza_shift_2025.presentation.screens.payment.ui.paid

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nmichail.pizza_shift_2025.presentation.theme.OrangePizza

@Composable
fun SuccessfulActionButtons(
    onOrderDetails: () -> Unit,
    onMain: () -> Unit
) {
    OutlinedButton(
        onClick = onOrderDetails,
        modifier = Modifier
            .width(368.dp)
            .height(66.dp)
            .padding(bottom = 12.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text("Детали заказа", fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurface)
    }
    Button(
        onClick = onMain,
        modifier = Modifier
            .width(368.dp)
            .height(66.dp),
        colors = ButtonDefaults.buttonColors(containerColor = OrangePizza),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text("На главную", fontSize = 18.sp, color = Color.White)
    }
} 
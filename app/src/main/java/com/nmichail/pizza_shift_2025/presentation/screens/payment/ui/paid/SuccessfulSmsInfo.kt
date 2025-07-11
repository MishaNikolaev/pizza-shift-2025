package com.nmichail.pizza_shift_2025.presentation.screens.payment.ui.paid

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SuccessfulSmsInfo(modifier: Modifier = Modifier) {
    Text(
        text = "Вся информация была продублирована в SMS",
        fontSize = 14.sp,
        color = Color.Gray,
        modifier = modifier.padding(bottom = 34.dp)
    )
} 
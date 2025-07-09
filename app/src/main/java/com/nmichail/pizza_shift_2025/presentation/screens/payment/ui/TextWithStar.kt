package com.nmichail.pizza_shift_2025.presentation.screens.payment.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TextWithStar(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = text, fontSize = 16.sp, color = Color.Black)
        Text(text = "*", fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Bold)
    }
}
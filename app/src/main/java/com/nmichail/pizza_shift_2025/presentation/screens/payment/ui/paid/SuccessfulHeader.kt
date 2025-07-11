package com.nmichail.pizza_shift_2025.presentation.screens.payment.ui.paid

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nmichail.pizza_shift_2025.R

@Composable
fun SuccessfulHeader() {
    Surface(
        shape = RoundedCornerShape(100),
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.size(80.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.accept),
                contentDescription = "Успех",
                modifier = Modifier.size(48.dp)
            )
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
    Text(
        text = "Оплата прошла успешно!",
        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(bottom = 16.dp)
    )
} 
package com.nmichail.pizza_shift_2025.presentation.screens.payment.ui.paid

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nmichail.pizza_shift_2025.R

@Composable
fun SuccessfulCloseButton(
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClose,
        modifier = modifier.padding(12.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.cancel),
            contentDescription = "Закрыть",
            tint = Color.LightGray,
            modifier = Modifier.size(20.dp)
        )
    }
} 
package com.nmichail.pizza_shift_2025.presentation.screens.orders.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun OrdersTabSelector(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val bgColor = MaterialTheme.colorScheme.onPrimary
    val shape = RoundedCornerShape(16.dp)
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .height(40.dp)
            .background(bgColor, shape)
            .clip(shape)
    ) {
        TabPill(
            text = "Активные",
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) },
            modifier = Modifier.weight(1f)
        )
        TabPill(
            text = "История",
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) },
            modifier = Modifier.weight(1f)
        )
    }
}
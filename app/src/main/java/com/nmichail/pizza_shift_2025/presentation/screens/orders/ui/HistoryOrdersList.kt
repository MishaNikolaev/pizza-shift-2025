package com.nmichail.pizza_shift_2025.presentation.screens.orders.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nmichail.pizza_shift_2025.data.dto.PizzaOrderDto

@Composable
fun HistoryOrdersList(orders: List<PizzaOrderDto>, onDetailsClick: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(orders, key = { it.id ?: it.hashCode() }) { order ->
            OrderCard(
                order = order,
                onDetailsClick = { onDetailsClick(order.id ?: "") },
                showCancelButton = false
            )
        }
    }
}
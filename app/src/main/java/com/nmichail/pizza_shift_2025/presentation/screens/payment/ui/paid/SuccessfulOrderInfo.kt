package com.nmichail.pizza_shift_2025.presentation.screens.payment.ui.paid

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nmichail.pizza_shift_2025.data.dto.PizzaOrderDto
import com.nmichail.pizza_shift_2025.presentation.util.toReadableSizeName
import com.nmichail.pizza_shift_2025.presentation.util.toReadableTopping

@Composable
fun SuccessfulOrderInfo(
    lastOrder: PizzaOrderDto?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Заказ",
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = lastOrder?.pizzas?.joinToString("\n") { pizza ->
                val name = pizza.name ?: "Пицца"
                val size = pizza.sizes.firstOrNull()?.type?.toReadableSizeName() ?: ""
                val toppings = pizza.toppings.map { it.type.toReadableTopping() }
                val toppingsText = when {
                    toppings.isEmpty() -> ""
                    toppings.size == 1 -> "+${toppings[0]}"
                    else -> "+${toppings[0]}, " + toppings.drop(1).joinToString(", ")
                }
                listOf(name, size, toppingsText).filter { it.isNotBlank() }.joinToString(", ")
            } ?: "-",
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 12.dp)
        )
    }
} 
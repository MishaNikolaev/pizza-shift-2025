package com.nmichail.pizza_shift_2025.presentation.screens.orders.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nmichail.pizza_shift_2025.data.dto.PizzaOrderDto
import com.nmichail.pizza_shift_2025.presentation.theme.OrangePizza

@Composable
fun OrderCard(
    order: PizzaOrderDto,
    onDetailsClick: () -> Unit,
    showCancelButton: Boolean,
    onCancelClick: (() -> Unit)? = null
) {
    val statusText = when (order.status) {
        0 -> "Оформлен"
        1 -> "В пути"
        2 -> "Доставлен"
        3 -> "Отменён"
        4 -> "Ошибка"
        else -> "-"
    }
    val statusColor = when (order.status) {
        2 -> Color(0xFF4CAF50)
        3 -> Color(0xFFFF3B30)
        4 -> Color(0xFFFF3B30)
        else -> Color(0xFFFFC107)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background((MaterialTheme.colorScheme.background), RoundedCornerShape(20.dp))
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Статус", color = MaterialTheme.colorScheme.secondaryContainer, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(statusColor, shape = RoundedCornerShape(50))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(statusText, color = MaterialTheme.colorScheme.inversePrimary, fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Адрес доставки", color = MaterialTheme.colorScheme.secondaryContainer, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(order.receiverAddress.let { "${it.street}, д. ${it.house}" }, color = MaterialTheme.colorScheme.inversePrimary, fontSize = 16.sp, modifier = Modifier.padding(bottom = 8.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text("Состав заказа", color = MaterialTheme.colorScheme.secondaryContainer, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(order.pizzas.joinToString("\n") { it.name }, color = MaterialTheme.colorScheme.inversePrimary, fontSize = 16.sp, modifier = Modifier.padding(bottom = 8.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text("Сумма заказа", color = Color.Gray, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("${order.totalPrice} р", color = MaterialTheme.colorScheme.inversePrimary, fontSize = 16.sp, modifier = Modifier.padding(bottom = 16.dp))
                if (showCancelButton) {
                    Button(
                        onClick = { onCancelClick?.invoke() },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = OrangePizza)
                    ) {
                        Text("Отменить заказ", color = Color.White, fontSize = 17.sp)
                    }
                } else {
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Подробнее",
                            color = MaterialTheme.colorScheme.inversePrimary,
                            fontSize = 15.sp,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier
                                .clickable { onDetailsClick() }
                        )
                    }
                }
            }
        }
    }
}
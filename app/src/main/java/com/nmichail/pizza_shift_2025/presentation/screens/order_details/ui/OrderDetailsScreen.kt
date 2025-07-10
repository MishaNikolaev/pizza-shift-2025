package com.nmichail.pizza_shift_2025.presentation.screens.order_details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.res.painterResource
import com.nmichail.pizza_shift_2025.presentation.theme.OrangePizza
import com.nmichail.pizza_shift_2025.R
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.nmichail.pizza_shift_2025.presentation.screens.cart.presentation.CartViewModel
import androidx.navigation.NavController
import com.nmichail.pizza_shift_2025.presentation.navigation.Screen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import com.nmichail.pizza_shift_2025.presentation.screens.order_details.presentation.OrderDetailViewModel

@Composable
fun OrderDetailsScreen(orderId: String, onBack: () -> Unit, navController: NavController? = null, onTabChange: ((tab: com.nmichail.pizza_shift_2025.presentation.components.BottomBarTab) -> Unit)? = null) {
    val viewModel: OrderDetailViewModel = hiltViewModel()
    val order by viewModel.order.collectAsState()
    val cartViewModel: CartViewModel = hiltViewModel()

    LaunchedEffect(orderId) {
        viewModel.loadOrder(orderId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_left),
                        contentDescription = "Назад",
                        tint = MaterialTheme.colorScheme.inversePrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Детали заказа",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.inversePrimary
                )
                Spacer(modifier = Modifier.width(48.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (order == null) {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                val statusText = when (order!!.status) {
                    0 -> "Оформлен"
                    1 -> "В пути"
                    2 -> "Доставлен"
                    3 -> "Отменён"
                    else -> "-"
                }
                val statusColor = when (order!!.status) {
                    2 -> Color(0xFF4CAF50)
                    3 -> Color(0xFFFF3B30)
                    else -> Color(0xFFFFC107)
                }
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Статус", color = Color.Gray, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .background(statusColor, shape = RoundedCornerShape(50))
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(statusText, color = MaterialTheme.colorScheme.inversePrimary, fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Адрес доставки", color = Color.Gray, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(order!!.receiverAddress.let { "${it.street}, д. ${it.house}${if (it.apartment.isNotBlank()) ", кв. ${it.apartment}" else ""}${if (!it.comment.isNullOrBlank()) ", ${it.comment}" else ""}" }, color = MaterialTheme.colorScheme.inversePrimary, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Состав заказа", color = Color.Gray, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(order!!.pizzas.joinToString("\n") { it.name }, color = MaterialTheme.colorScheme.inversePrimary, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Сумма заказа", color = Color.Gray, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("${order!!.totalPrice} р", color = MaterialTheme.colorScheme.inversePrimary, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = {
                                if (order != null) {
                                    cartViewModel.repeatOrder(order!!)
                                    onTabChange?.invoke(com.nmichail.pizza_shift_2025.presentation.components.BottomBarTab.CART)
                                    navController?.navigate(Screen.Cart.route)
                                }
                            },
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = OrangePizza)
                        ) {
                            Text("Повторить заказ", color = Color.White, fontSize = 17.sp)
                        }
                    }
                }
            }
        }
    }
}

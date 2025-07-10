package com.nmichail.pizza_shift_2025.presentation.screens.cart.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nmichail.pizza_shift_2025.R
import com.nmichail.pizza_shift_2025.presentation.navigation.Screen
import com.nmichail.pizza_shift_2025.presentation.screens.cart.presentation.CartUiState
import com.nmichail.pizza_shift_2025.presentation.screens.cart.presentation.CartViewModel
import com.nmichail.pizza_shift_2025.presentation.theme.OrangePizza

@Composable
fun CartScreen(
    navController: NavController,
    onNavigateToCatalog: () -> Unit,
    viewModel: CartViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    when (val cartState = state) {
        is CartUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is CartUiState.Content -> {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateToCatalog() }
                            .padding(start = 12.dp, top = 16.dp, bottom = 12.dp, end = 16.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_left),
                            contentDescription = "Назад",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Корзина",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            ),
                        )
                    }
                    Divider(color = MaterialTheme.colorScheme.tertiaryContainer, thickness = 1.dp)
                    if (cartState.items.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Корзина пуста",
                                color = Color.Gray,
                                fontSize = 18.sp
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(top = 8.dp, bottom = 120.dp),
                        ) {
                            items(cartState.items) { cartItem ->
                                CartItemRow(
                                    cartItem = cartItem,
                                    onIncrease = { viewModel.updateCount(cartItem.id, cartItem.count + 1) },
                                    onDecrease = { viewModel.updateCount(cartItem.id, cartItem.count - 1) },
                                    onRemove = { viewModel.removeItem(cartItem.id) },
                                    onEdit = {
                                        val route = Screen.CatalogDetail.createRoute(
                                            pizzaId = cartItem.pizza.id,
                                            size = cartItem.selectedSize,
                                            toppings = cartItem.selectedToppings,
                                            cartItemId = cartItem.id
                                        )
                                        navController.navigate(route)
                                    }
                                )
                                Divider(
                                    color = Color(0xFFF3F3F6),
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 20.dp)
                                )
                            }
                        }
                    }
                }
                if (cartState.items.isNotEmpty()) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .shadow(8.dp, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 30.dp, vertical = 40.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Стоимость заказа:",
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${cartState.totalPrice} р",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    navController.navigate(Screen.Payment.route)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = OrangePizza)
                            ) {
                                Text(
                                    text = "Оформить заказ",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
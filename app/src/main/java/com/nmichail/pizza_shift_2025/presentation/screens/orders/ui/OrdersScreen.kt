package com.nmichail.pizza_shift_2025.presentation.screens.orders.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.nmichail.pizza_shift_2025.R
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import com.nmichail.pizza_shift_2025.presentation.theme.OrangePizza
import androidx.compose.ui.text.style.TextDecoration
import androidx.hilt.navigation.compose.hiltViewModel
import com.nmichail.pizza_shift_2025.presentation.screens.orders.OrdersViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.material3.CircularProgressIndicator
import com.nmichail.pizza_shift_2025.data.dto.PizzaOrderDto
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.DisposableEffect
import kotlinx.coroutines.delay
import android.util.Log
import androidx.compose.material3.MaterialTheme

@Composable
fun OrdersScreen(onDetailsClick: (String) -> Unit, navController: NavController? = null) {
    val viewModel: OrdersViewModel = hiltViewModel()
    val orders by viewModel.orders.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }
    var showCancelDialog by remember { mutableStateOf(false) }
    var orderToCancel by remember { mutableStateOf<String?>(null) }


    LaunchedEffect(Unit) {
        viewModel.loadOrders()
        while (true) {
            delay(60_000)
            viewModel.loadOrders()
        }
    }

    Log.d("OrdersScreen", "orders: ${orders.map { it.id to it.status }}")

    if (navController != null) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        DisposableEffect(navBackStackEntry) {
            val observer = NavController.OnDestinationChangedListener { controller, destination, arguments ->
                if (destination.route?.contains("orders") == true) {
                    viewModel.loadOrders()
                }
            }
            navController.addOnDestinationChangedListener(observer)
            onDispose {
                navController.removeOnDestinationChangedListener(observer)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {

        Text(
            text = "Заказы",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            modifier = Modifier.padding(start = 24.dp, top = 24.dp, bottom = 12.dp)
        )
        Spacer(modifier = Modifier.height(14.dp))
        OrdersTabSelector(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
        Spacer(modifier = Modifier.height(16.dp))
        if (loading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (error != null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Ошибка: $error", color = Color.Red)
            }
        } else {
            val activeOrders = orders.filter { it.status == 0 || it.status == 1 }
            val historyOrders = orders.filter { it.status != 0 && it.status != 1 }
            if (selectedTab == 0) {
                ActiveOrdersList(
                    orders = activeOrders,
                    onDetailsClick = onDetailsClick,
                    onCancelClick = { orderId ->
                        orderToCancel = orderId
                        showCancelDialog = true
                    }
                )
            } else {
                HistoryOrdersList(
                    orders = historyOrders,
                    onDetailsClick = onDetailsClick
                )
            }
        }
    }
    if (showCancelDialog && orderToCancel != null) {
        CancelOrderDialog(
            onDismiss = { showCancelDialog = false },
            onConfirm = {
                viewModel.cancelOrder(orderToCancel!!) {
                    showCancelDialog = false
                    orderToCancel = null
                }
            }
        )
    }
}
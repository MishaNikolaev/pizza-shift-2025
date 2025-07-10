package com.nmichail.pizza_shift_2025.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.currentCompositeKeyHash
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nmichail.pizza_shift_2025.presentation.components.BottomBar
import com.nmichail.pizza_shift_2025.presentation.components.BottomBarTab
import com.nmichail.pizza_shift_2025.presentation.screens.auth.presentation.AuthUiState
import com.nmichail.pizza_shift_2025.presentation.screens.auth.ui.AuthScreen
import com.nmichail.pizza_shift_2025.presentation.screens.auth.presentation.AuthViewModel
import com.nmichail.pizza_shift_2025.presentation.screens.catalog.ui.CatalogScreen
import com.nmichail.pizza_shift_2025.presentation.screens.catalog_detail.ui.CatalogDetailScreen
import com.nmichail.pizza_shift_2025.presentation.screens.orders.ui.OrdersScreen
import com.nmichail.pizza_shift_2025.presentation.screens.cart.ui.CartScreen
import com.nmichail.pizza_shift_2025.presentation.screens.payment.presentation.PaymentViewModel
import com.nmichail.pizza_shift_2025.presentation.screens.payment.ui.CardPaymentScreen
import com.nmichail.pizza_shift_2025.presentation.screens.profile.ui.ProfileScreen
import com.nmichail.pizza_shift_2025.presentation.screens.payment.ui.PaymentScreen
import com.nmichail.pizza_shift_2025.presentation.screens.payment.ui.SuccessfulScreen
import com.nmichail.pizza_shift_2025.presentation.screens.order_details.ui.OrderDetailsScreen
import kotlinx.coroutines.Dispatchers

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object Catalog : Screen("catalog")
    object CatalogDetail : Screen("catalog_detail/{pizzaId}?size={size}&toppings={toppings}&cartItemId={cartItemId}") {
        fun createRoute(pizzaId: String, size: String? = null, toppings: Set<String>? = null, cartItemId: String? = null): String {
            val sizePart = size?.let { "&size=$it" } ?: ""
            val toppingsPart = toppings?.takeIf { it.isNotEmpty() }?.joinToString(",")?.let { "&toppings=$it" } ?: ""
            val cartItemIdPart = cartItemId?.let { "&cartItemId=$it" } ?: ""
            return "catalog_detail/$pizzaId?${sizePart.removePrefix("&")}${if (sizePart.isNotEmpty() && toppingsPart.isNotEmpty()) "&" else ""}${toppingsPart.removePrefix("&")}${cartItemIdPart}".removeSuffix("?")
        }
    }
    object Orders : Screen("orders")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object Payment : Screen("payment")
    object CardPayment : Screen("card_payment")
    object Successful : Screen("successful")
    object OrderDetails : Screen("order_details/{orderId}") {
        fun createRoute(orderId: String) = "order_details/$orderId"
    }
}

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    val viewModel: AuthViewModel = hiltViewModel()
    val isAuthorized by viewModel.isAuthorized.collectAsState()
    val isAuthCheckFinished by viewModel.isAuthCheckFinished.collectAsState()
    var currentTab by remember { mutableStateOf(BottomBarTab.PIZZA) }

    LaunchedEffect(Unit) {
        viewModel.checkAuthorization()
    }

    if (!isAuthCheckFinished) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val startDestination = if (isAuthorized) Screen.Catalog.route else Screen.Auth.route

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            if (isAuthorized && currentRoute != Screen.Payment.route && currentRoute != Screen.CardPayment.route) {
                BottomBar(
                    currentTab = currentTab,
                    onTabSelected = { tab ->
                        currentTab = tab
                        when (tab) {
                            BottomBarTab.PIZZA -> navController.navigate(Screen.Catalog.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            BottomBarTab.ORDERS -> navController.navigate(Screen.Orders.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            BottomBarTab.CART -> navController.navigate(Screen.Cart.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            BottomBarTab.PROFILE -> navController.navigate(Screen.Profile.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Auth.route) {
                AuthScreen(
                    onAuthSuccess = {
                        viewModel.checkAuthorization()
                        navController.navigate(Screen.Catalog.route) {
                            popUpTo(Screen.Auth.route) { inclusive = true }
                        }
                    }
                )
            }
            
            composable(Screen.Catalog.route) {
                CatalogScreen(
                    onPizzaClick = { pizzaId ->
                        navController.navigate(Screen.CatalogDetail.createRoute(pizzaId))
                    }
                )
            }
            
            composable(Screen.CatalogDetail.route) { backStackEntry ->
                val pizzaId = requireNotNull(backStackEntry.arguments?.getString("pizzaId")) { "pizzaId is required for CatalogDetailScreen" }
                val size = backStackEntry.arguments?.getString("size")
                val toppingsString = backStackEntry.arguments?.getString("toppings")
                val toppings = toppingsString?.split(",")?.filter { it.isNotBlank() }?.toSet() ?: emptySet()
                val cartItemId = backStackEntry.arguments?.getString("cartItemId")
                CatalogDetailScreen(
                    pizzaId = pizzaId,
                    initialSize = size,
                    initialToppings = toppings,
                    cartItemId = cartItemId,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
            
            composable(Screen.Orders.route) {
                OrdersScreen(
                    onDetailsClick = { orderId ->
                        navController.navigate(Screen.OrderDetails.createRoute(orderId))
                    }
                )
            }
            
            composable(Screen.Cart.route) {
                CartScreen(navController = navController, onNavigateToCatalog = {
                    currentTab = BottomBarTab.PIZZA
                    navController.navigate(Screen.Catalog.route) {
                        popUpTo(Screen.Catalog.route) { inclusive = false }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
            }
            
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(Screen.Payment.route) { backStackEntry ->
                val authUiState by viewModel.uiState.collectAsState()
                val phoneFromFlow by viewModel.authorizedPhoneFlow.collectAsState()
                val phone = phoneFromFlow?.takeIf { !it.isNullOrBlank() } ?: when (authUiState) {
                    is AuthUiState.EnterPhone -> (authUiState as AuthUiState.EnterPhone).phone
                    is AuthUiState.EnterCode -> (authUiState as AuthUiState.EnterCode).phone
                    else -> ""
                }
                val parentEntry = remember(navController) {
                    navController.getBackStackEntry(Screen.Payment.route)
                }
                val paymentViewModel: PaymentViewModel = hiltViewModel(parentEntry)
                PaymentScreen(
                    viewModel = paymentViewModel,
                    phoneFromAuth = phone,
                    onBack = { navController.popBackStack() },
                    onContinue = { navController.navigate(Screen.CardPayment.route) }
                )
            }
            composable(Screen.CardPayment.route) {
                val parentEntry = remember(navController) {
                    navController.getBackStackEntry(Screen.Payment.route)
                }
                val paymentViewModel: PaymentViewModel = hiltViewModel(parentEntry)
                CardPaymentScreen(
                    viewModel = paymentViewModel,
                    onBack = { navController.popBackStack() },
                    onPay = { navController.navigate(Screen.Successful.route) }
                )
            }
            composable(Screen.Successful.route) {
                SuccessfulScreen(
                    onClose = {
                        currentTab = BottomBarTab.PIZZA
                        navController.navigate(Screen.Catalog.route) {
                            popUpTo(Screen.Catalog.route) { inclusive = false }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onOrderDetails = {
                        currentTab = BottomBarTab.ORDERS
                        navController.navigate(Screen.Orders.route) {
                            popUpTo(Screen.Catalog.route) { inclusive = false }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onMain = {
                        currentTab = BottomBarTab.PIZZA
                        navController.navigate(Screen.Catalog.route) {
                            popUpTo(Screen.Catalog.route) { inclusive = false }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
            composable(Screen.OrderDetails.route) { backStackEntry ->
                val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
                OrderDetailsScreen(
                    orderId = orderId,
                    onBack = { navController.popBackStack() },
                    navController = navController,
                    onTabChange = { tab -> currentTab = tab }
                )
            }
        }
    }
} 
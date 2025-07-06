package com.nmichail.pizza_shift_2025.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.nmichail.pizza_shift_2025.presentation.components.BottomBar
import com.nmichail.pizza_shift_2025.presentation.components.BottomBarTab
import com.nmichail.pizza_shift_2025.presentation.screens.auth.ui.AuthScreen
import com.nmichail.pizza_shift_2025.presentation.screens.auth.presentation.AuthViewModel
import com.nmichail.pizza_shift_2025.presentation.screens.catalog.ui.CatalogScreen
import com.nmichail.pizza_shift_2025.presentation.screens.catalog_detail.ui.CatalogDetailScreen
import com.nmichail.pizza_shift_2025.presentation.screens.orders.ui.OrdersScreen
import com.nmichail.pizza_shift_2025.presentation.screens.cart.ui.CartScreen
import com.nmichail.pizza_shift_2025.presentation.screens.profile.ui.ProfileScreen

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object Catalog : Screen("catalog")
    object CatalogDetail : Screen("catalog_detail/{pizzaId}") {
        fun createRoute(pizzaId: String) = "catalog_detail/$pizzaId"
    }
    object Orders : Screen("orders")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
}

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
            if (isAuthorized) {
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
                val pizzaId = backStackEntry.arguments?.getString("pizzaId") ?: ""
                CatalogDetailScreen(
                    pizzaId = pizzaId,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
            
            composable(Screen.Orders.route) {
                OrdersScreen()
            }
            
            composable(Screen.Cart.route) {
                CartScreen()
            }
            
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }
} 
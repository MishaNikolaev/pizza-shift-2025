package com.nmichail.pizza_shift_2025.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nmichail.pizza_shift_2025.presentation.screens.auth.AuthScreen
import com.nmichail.pizza_shift_2025.presentation.screens.catalog.CatalogScreen

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object Main : Screen("main") 
}

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Auth.route,
        modifier = modifier
    ) {
        composable(Screen.Auth.route) {
            AuthScreen(
                onAuthSuccess = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Auth.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Main.route) {
            CatalogScreen()
        }
    }
} 
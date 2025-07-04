package com.nmichail.pizza_shift_2025.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nmichail.pizza_shift_2025.presentation.screens.auth.ui.AuthScreen
import com.nmichail.pizza_shift_2025.presentation.screens.catalog.ui.CatalogScreen
import com.nmichail.pizza_shift_2025.presentation.screens.auth.presentation.AuthViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object Main : Screen("main") 
}

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val viewModel: AuthViewModel = hiltViewModel()
    val isAuthorized by viewModel.isAuthorized.collectAsState()
    val checked = rememberSaveable { mutableStateOf(false) }
    val isAuthCheckFinished by viewModel.isAuthCheckFinished.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkAuthorization()
        checked.value = true
    }

    if (!isAuthCheckFinished) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val startDestination = if (isAuthorized) Screen.Main.route else Screen.Auth.route

    NavHost(
        navController = navController,
        startDestination = startDestination,
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
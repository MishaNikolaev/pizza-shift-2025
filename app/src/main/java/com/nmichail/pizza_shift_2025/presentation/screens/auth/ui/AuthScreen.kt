package com.nmichail.pizza_shift_2025.presentation.screens.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.nmichail.pizza_shift_2025.presentation.screens.auth.presentation.AuthScreenState
import com.nmichail.pizza_shift_2025.presentation.screens.auth.presentation.AuthViewModel
import com.nmichail.pizza_shift_2025.presentation.screens.auth.presentation.AuthUiState

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onAuthSuccess: () -> Unit = {}
) {

    LaunchedEffect(Unit) {
        viewModel.checkAuthorization()
    }

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.isAuthorized.collect { authorized ->
            if (authorized) {
                onAuthSuccess()
            }
        }
    }

    when (state) {
        is AuthUiState.Loading -> {}
        is AuthUiState.EnterPhone -> EnterPhoneScreen(state as AuthUiState.EnterPhone, viewModel)
        is AuthUiState.EnterCode -> EnterCodeScreen(state as AuthUiState.EnterCode, viewModel)
    }
}
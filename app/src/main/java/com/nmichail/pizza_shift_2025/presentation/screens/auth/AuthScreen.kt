package com.nmichail.pizza_shift_2025.presentation.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nmichail.pizza_shift_2025.presentation.screens.auth.components.EnterCodeScreen
import com.nmichail.pizza_shift_2025.presentation.screens.auth.components.EnterPhoneScreen

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onAuthSuccess: () -> Unit = {}
) {

    LaunchedEffect(Unit) {
        viewModel.checkAuthorization()
    }

    val state by viewModel.uiState.collectAsState()
    val isAuthorized by viewModel.isAuthorized.collectAsState()

    if (isAuthorized) {
        LaunchedEffect(Unit) {
            onAuthSuccess()
        }
    }
    when (val screen = state.screenState) {
        is AuthScreenState.EnterPhone -> EnterPhoneScreen(state, viewModel)
        is AuthScreenState.EnterCode -> EnterCodeScreen(state, viewModel)
    }
}
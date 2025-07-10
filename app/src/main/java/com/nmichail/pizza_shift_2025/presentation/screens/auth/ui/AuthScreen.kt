package com.nmichail.pizza_shift_2025.presentation.screens.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nmichail.pizza_shift_2025.presentation.screens.auth.presentation.AuthUiState
import com.nmichail.pizza_shift_2025.presentation.screens.auth.presentation.AuthViewModel
import com.nmichail.pizza_shift_2025.presentation.theme.OrangePizza

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (val currentState = state) {
            is AuthUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(48.dp),
                            color = OrangePizza
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Проверяем авторизацию...",
                            color = OrangePizza
                        )
                    }
                }
            }
            is AuthUiState.EnterPhone -> EnterPhoneScreen(currentState, viewModel)
            is AuthUiState.EnterCode -> EnterCodeScreen(currentState, viewModel)
        }
    }
}
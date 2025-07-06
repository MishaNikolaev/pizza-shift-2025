package com.nmichail.pizza_shift_2025.presentation.screens.auth.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nmichail.pizza_shift_2025.presentation.screens.auth.presentation.AuthScreenState
import com.nmichail.pizza_shift_2025.presentation.screens.auth.presentation.AuthViewModel
import com.nmichail.pizza_shift_2025.presentation.screens.auth.presentation.AuthUiState
import com.nmichail.pizza_shift_2025.presentation.theme.OrangePizza

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onAuthSuccess: () -> Unit = {}
) {

    LaunchedEffect(Unit) {
       // Log.d("AuthScreen", "Calling checkAuthorization() on start")
        viewModel.checkAuthorization()
    }

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.isAuthorized.collect { authorized ->
           // Log.d("AuthScreen", "isAuthorized.collect: $authorized")
            if (authorized) {
             //   Log.d("AuthScreen", "onAuthSuccess called, navigating to main")
                onAuthSuccess()
            }
        }
    }

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
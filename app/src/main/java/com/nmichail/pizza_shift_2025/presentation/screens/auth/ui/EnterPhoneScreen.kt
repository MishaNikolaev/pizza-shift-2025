package com.nmichail.pizza_shift_2025.presentation.screens.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nmichail.pizza_shift_2025.presentation.screens.auth.presentation.AuthUiState
import com.nmichail.pizza_shift_2025.presentation.screens.auth.presentation.AuthViewModel
import com.nmichail.pizza_shift_2025.presentation.screens.auth.presentation.OtpState
import com.nmichail.pizza_shift_2025.presentation.theme.OrangePizza
import com.nmichail.pizza_shift_2025.presentation.theme.PizzaButtonColors


@Composable
fun EnterPhoneScreen(state: AuthUiState.EnterPhone, viewModel: AuthViewModel) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Авторизация",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Введите номер телефона для входа в личный кабинет",
            fontSize = MaterialTheme.typography.bodyLarge.fontSize * 1.2
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.phone,
            onValueChange = viewModel::onPhoneChanged,
            label = { Text("Телефон", fontSize = 18.sp, color = Color.Gray) },
            textStyle = TextStyle(fontSize = 18.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
                cursorColor = Color.Gray
            ),
            enabled = state.otpState !is OtpState.Loading
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                focusManager.clearFocus()
                viewModel.onContinueClicked()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = state.otpState !is OtpState.Loading,
            colors = PizzaButtonColors
        ) {
            if (state.otpState is OtpState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White
                )
            } else {
                Text("Продолжить", fontSize = 20.sp)
            }
        }

        when (state.otpState) {
            is OtpState.Error -> {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = state.otpState.message, color = Color.Red)
            }

            is OtpState.Loading -> {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Отправляем код...", color = OrangePizza)
            }

            is OtpState.None -> {
            }
        }
    }
}
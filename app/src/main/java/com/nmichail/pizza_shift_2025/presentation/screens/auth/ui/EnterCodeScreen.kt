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
import com.nmichail.pizza_shift_2025.presentation.screens.auth.presentation.SignInState
import com.nmichail.pizza_shift_2025.presentation.theme.OrangePizza
import com.nmichail.pizza_shift_2025.presentation.theme.PizzaButtonColors


@Composable
fun EnterCodeScreen(state: AuthUiState.EnterCode, viewModel: AuthViewModel) {
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
            text = "Введите код для входа в личный кабинет",
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.phone,
            onValueChange = {},
            label = { Text("Телефон", fontSize = 18.sp, color = Color.Gray) },
            textStyle = TextStyle(fontSize = 18.sp),
            enabled = false,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
                cursorColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.code,
            onValueChange = viewModel::onCodeChanged,
            label = { Text("Код", fontSize = 18.sp, color = Color.Gray) },
            textStyle = TextStyle(fontSize = 18.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
                cursorColor = Color.Gray
            ),
            enabled = state.signInState !is SignInState.Loading
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                focusManager.clearFocus()
                viewModel.onSignInClicked()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = state.signInState !is SignInState.Loading,
            colors = PizzaButtonColors
        ) {
            if (state.signInState is SignInState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White
                )
            } else {
                Text("Войти", fontSize = 20.sp)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        if (state.secondsLeft > 0) {
            Text(
                text = "Запросить код повторно можно через ${state.secondsLeft} секунд",
                color = Color.Gray,
                fontSize = 18.sp
            )
        } else {
            TextButton(
                onClick = viewModel::onResendCodeClicked,
                enabled = state.signInState !is SignInState.Loading,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color.Black,
                    disabledContentColor = Color.Black.copy(alpha = 0.5f)
                )
            ) {
                Text(
                    "Запросить код ещё раз",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        when (state.signInState) {
            is SignInState.Error -> {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = state.signInState.userMessage,
                    color = Color.Red,
                    fontSize = 18.sp
                )
            }

            is SignInState.Loading -> {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Входим в систему...", color = OrangePizza, fontSize = 18.sp)
            }

            is SignInState.None -> {

            }
        }
    }
}
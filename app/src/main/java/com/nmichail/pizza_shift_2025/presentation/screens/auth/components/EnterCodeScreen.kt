package com.nmichail.pizza_shift_2025.presentation.screens.auth.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nmichail.pizza_shift_2025.presentation.screens.auth.AuthScreenState
import com.nmichail.pizza_shift_2025.presentation.screens.auth.AuthViewModel
import com.nmichail.pizza_shift_2025.presentation.theme.OrangePizza


@Composable
fun EnterCodeScreen(state: AuthUiState, viewModel: AuthViewModel) {
    val focusManager = LocalFocusManager.current
    val screen = state.screenState as? AuthScreenState.EnterCode ?: return
    Column(
        modifier = Modifier
            .fillMaxSize()
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
            label = { Text("Телефон", fontSize = 18.sp, color = Color.Black) },
            textStyle = TextStyle(fontSize = 18.sp),
            enabled = false,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                disabledBorderColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                disabledLabelColor = Color.Black,
                cursorColor = Color.Black
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.code,
            onValueChange = viewModel::onCodeChanged,
            label = { Text("Код", fontSize = 18.sp, color = Color.Black) },
            textStyle = TextStyle(fontSize = 18.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                disabledBorderColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                disabledLabelColor = Color.Black,
                cursorColor = Color.Black
            )
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
            enabled = !state.isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = OrangePizza,
                contentColor = Color.White,
                disabledContainerColor = OrangePizza.copy(alpha = 0.5f),
                disabledContentColor = Color.White.copy(alpha = 0.5f)
            )
        ) {
            Text("Войти", fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(12.dp))
        if (screen.secondsLeft > 0) {
            Text(
                text = "Запросить код повторно можно через ${screen.secondsLeft} секунд",
                color = Color.Gray,
                fontSize = 18.sp
            )
        } else {
            TextButton(
                onClick = viewModel::onResendCodeClicked,
                enabled = !state.isLoading,
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
        if (state.error != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = if (state.error?.contains("400") == true || state.error?.contains("Bad Request") == true) {
                    "Вы ввели неверный код. Попробуйте ещё раз."
                } else state.error ?: "",
                color = Color.Red,
                fontSize = 18.sp
            )
        }
    }
}
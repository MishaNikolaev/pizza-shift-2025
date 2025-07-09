package com.nmichail.pizza_shift_2025.presentation.screens.payment.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nmichail.pizza_shift_2025.presentation.screens.payment.presentation.PaymentViewModel
import com.nmichail.pizza_shift_2025.presentation.screens.payment.presentation.PaymentUiState
import com.nmichail.pizza_shift_2025.presentation.theme.OrangePizza
import androidx.hilt.navigation.compose.hiltViewModel
import com.nmichail.pizza_shift_2025.R
import com.nmichail.pizza_shift_2025.presentation.theme.PizzaTextFieldColors
import com.nmichail.pizza_shift_2025.presentation.theme.PizzaTextFieldGrayCursorColors
import com.nmichail.pizza_shift_2025.presentation.theme.PizzaTextFieldGrayBgColors
import com.nmichail.pizza_shift_2025.presentation.theme.PizzaTextFieldAllGrayColors
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.nmichail.pizza_shift_2025.presentation.theme.PizzaOutlinedTextField

@Composable
fun PaymentScreen(
    viewModel: PaymentViewModel = hiltViewModel(),
    phoneFromAuth: String,
    onBack: () -> Unit,
    onContinue: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    painterResource(R.drawable.arrow_left),
                    contentDescription = "Назад",
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Ваши данные",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Шаг 1 из 2",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color.LightGray)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight()
                    .background(Color(0xFF4CAF50))
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            TextWithStar(text = "Фамилия")
            Spacer(modifier = Modifier.height(8.dp))
            PizzaOutlinedTextField(
                value = state.lastname,
                onValueChange = viewModel::onLastnameChanged,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))
            TextWithStar(text = "Имя")
            Spacer(modifier = Modifier.height(8.dp))
            PizzaOutlinedTextField(
                value = state.firstname,
                onValueChange = viewModel::onFirstnameChanged,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))
            TextWithStar(text = "Номер телефона")
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = phoneFromAuth,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = false,
                textStyle = TextStyle(color = Color.Gray),
                colors = PizzaTextFieldAllGrayColors.copy(
                    disabledContainerColor = Color(0xFFF5F5F5)
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Email", fontSize = 16.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            PizzaOutlinedTextField(
                value = state.email,
                onValueChange = viewModel::onEmailChanged,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Город", fontSize = 16.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            PizzaOutlinedTextField(
                value = state.city,
                onValueChange = viewModel::onCityChanged,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(64.dp))
        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = OrangePizza)
        ) {
            Text("Продолжить", fontSize = 20.sp, color = Color.White)
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}
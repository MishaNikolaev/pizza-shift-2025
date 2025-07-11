package com.nmichail.pizza_shift_2025.presentation.screens.payment.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nmichail.pizza_shift_2025.R
import com.nmichail.pizza_shift_2025.presentation.screens.payment.presentation.PaymentViewModel
import com.nmichail.pizza_shift_2025.presentation.theme.OrangePizza
import com.nmichail.pizza_shift_2025.presentation.theme.PizzaOutlinedTextField
import com.nmichail.pizza_shift_2025.presentation.theme.PizzaTextFieldAllGrayColors

@Composable
fun PaymentScreen(
    viewModel: PaymentViewModel = hiltViewModel(),
    phoneFromAuth: String,
    onBack: () -> Unit,
    onContinue: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    LaunchedEffect(phoneFromAuth, state.phone) {
        if (phoneFromAuth.isNotBlank() && state.phone.isBlank()) {
            viewModel.handlePhoneChanged(phoneFromAuth)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
            color = MaterialTheme.colorScheme.onSurface,
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
                onValueChange = viewModel::handleLastnameChanged,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))
            TextWithStar(text = "Имя")
            Spacer(modifier = Modifier.height(8.dp))
            PizzaOutlinedTextField(
                value = state.firstname,
                onValueChange = viewModel::handleFirstnameChanged,
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
            Text(text = "Email", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(8.dp))
            PizzaOutlinedTextField(
                value = state.email,
                onValueChange = viewModel::handleEmailChanged,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Город", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(8.dp))
            PizzaOutlinedTextField(
                value = state.city,
                onValueChange = viewModel::handleCityChanged,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))
            TextWithStar(text = "Улица")
            Spacer(modifier = Modifier.height(8.dp))
            PizzaOutlinedTextField(
                value = state.street,
                onValueChange = viewModel::handleStreetChanged,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))
            TextWithStar(text = "Дом")
            Spacer(modifier = Modifier.height(8.dp))
            PizzaOutlinedTextField(
                value = state.house,
                onValueChange = viewModel::handleHouseChanged,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))
            TextWithStar(text = "Квартира")
            Spacer(modifier = Modifier.height(8.dp))
            PizzaOutlinedTextField(
                value = state.apartment,
                onValueChange = viewModel::handleApartmentChanged,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Комментарий к заказу", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(8.dp))
            PizzaOutlinedTextField(
                value = state.comment,
                onValueChange = viewModel::handleCommentChanged,
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
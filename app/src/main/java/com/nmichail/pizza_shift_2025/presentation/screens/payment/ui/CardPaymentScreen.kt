package com.nmichail.pizza_shift_2025.presentation.screens.payment.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nmichail.pizza_shift_2025.R
import com.nmichail.pizza_shift_2025.presentation.screens.payment.presentation.PaymentViewModel
import com.nmichail.pizza_shift_2025.presentation.theme.OrangePizza

@Composable
fun CardPaymentScreen(
    onBack: (() -> Unit)? = null,
    onPay: (() -> Unit)? = null,
    viewModel: PaymentViewModel = hiltViewModel()
) {
    var cardNumberRaw by remember { mutableStateOf("") }
    val cardNumber = cardNumberRaw.chunked(4).joinToString(" ").take(19)
    var cardDateRaw by remember { mutableStateOf("") }
    val cardDate = when {
        cardDateRaw.isEmpty() -> ""
        cardDateRaw.length <= 2 -> cardDateRaw
        else -> cardDateRaw.take(2) + "/" + cardDateRaw.drop(2).take(2)
    }
    var cardCvv by remember { mutableStateOf("") }
    val paySuccess by viewModel.paySuccess.collectAsState()
    var localError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(paySuccess) {
        if (paySuccess) {
            onPay?.invoke()
            viewModel.resetPaySuccess()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
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
                onClick = { onBack?.invoke() },
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
                text = "Карта оплаты",
                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 22.sp, fontWeight = FontWeight.Bold)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Шаг 2 из 2",
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
                    .fillMaxWidth(1f)
                    .fillMaxHeight()
                    .background(Color(0xFF4CAF50))
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(16.dp))
                .padding(20.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Номер*", fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = {
                        val digits = it.filter { ch -> ch.isDigit() }
                        if (it.length < cardNumber.length) {
                            cardNumberRaw = digits.take(16)
                        } else {
                            cardNumberRaw = digits.take(16)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        focusedLabelColor = Color.Gray,
                        unfocusedLabelColor = Color.Gray,
                        cursorColor = Color.Black,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Срок*", fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = cardDate,
                            onValueChange = {
                                val digits = it.filter { ch -> ch.isDigit() }
                                if (it.length < cardDate.length) {
                                    cardDateRaw = digits.take(4)
                                } else {
                                    cardDateRaw = digits.take(4)
                                }
                            },
                            placeholder = { Text("00/00", color = Color.Gray) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Gray,
                                unfocusedLabelColor = Color.Gray,
                                cursorColor = Color.Black,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "CVV*", fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = cardCvv,
                            onValueChange = {
                                val digits = it.filter { ch -> ch.isDigit() }
                                cardCvv = digits.take(3)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Gray,
                                unfocusedLabelColor = Color.Gray,
                                cursorColor = Color.Black,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                localError = null
                viewModel.pay(
                    cardNumber = cardNumberRaw,
                    cardDate = cardDateRaw,
                    cardCvv = cardCvv
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = OrangePizza),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Оплатить", fontSize = 20.sp, color = Color.White)
        }
        if (localError != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(localError ?: "", color = Color.Red)
        }
    }
}
package com.nmichail.pizza_shift_2025.presentation.screens.payment.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nmichail.pizza_shift_2025.presentation.theme.OrangePizza
import com.nmichail.pizza_shift_2025.R

@Composable
fun SuccessfulScreen(
    onClose: () -> Unit = {},
    onOrderDetails: () -> Unit = {},
    onMain: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        IconButton(
            onClick = onClose,
            modifier = Modifier.align(Alignment.TopEnd).padding(12.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.cancel),
                contentDescription = "Закрыть",
                tint = Color.LightGray,
                modifier = Modifier.size(20.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(horizontal = 24.dp)
                .padding(top = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Surface(
                shape = RoundedCornerShape(100),
                color = Color(0xFFE6F7E6),
                modifier = Modifier.size(80.dp)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(R.drawable.accept),
                        contentDescription = "Успех",
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Оплата прошла успешно!",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Заказ",
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Пепперони, средняя 30 см,\nтрадиционное тесто + моцарелла, халапеньо\nСырная, большая 35 см, тонкое тесто",
                fontSize = 17.sp,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Start).padding(bottom = 12.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Адрес доставки",
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Россия, г. Новосибирск, ул. Кирова, д. 86",
                fontSize = 17.sp,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Start).padding(bottom = 12.dp)
            )
            Text(
                text = "Сумма заказа",
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "1240 р",
                fontSize = 17.sp,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Start).padding(bottom = 12.dp)
            )
            Text(
                text = "Вся информация была продублирована в SMS",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Start).padding(bottom = 34.dp)
            )
            OutlinedButton(
                onClick = onOrderDetails,
                modifier = Modifier
                    .width(368.dp)
                    .height(66.dp)
                    .padding(bottom = 12.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Детали заказа", fontSize = 18.sp, color = Color.Black)
            }
            Button(
                onClick = onMain,
                modifier = Modifier
                    .width(368.dp)
                    .height(66.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OrangePizza),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("На главную", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}
package com.nmichail.pizza_shift_2025.presentation.screens.catalog.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nmichail.pizza_shift_2025.domain.entities.Pizza


@Composable
fun PizzaCard(pizza: Pizza, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = pizza.imageUrl,
                contentDescription = pizza.name,
                modifier = Modifier
                    .size(116.dp)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = pizza.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(5.dp))
                pizza.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                        maxLines = 2
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "от ${pizza.price} ₽",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
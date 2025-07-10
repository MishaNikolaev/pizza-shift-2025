package com.nmichail.pizza_shift_2025.presentation.screens.catalog_detail.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.shadow
import coil.compose.AsyncImage
import com.nmichail.pizza_shift_2025.R
import androidx.hilt.navigation.compose.hiltViewModel
import com.nmichail.pizza_shift_2025.presentation.screens.catalog_detail.presentation.PizzaDetailViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.LaunchedEffect
import com.nmichail.pizza_shift_2025.domain.entities.PizzaTopping
import androidx.compose.ui.res.painterResource
import com.nmichail.pizza_shift_2025.presentation.theme.OrangePizza
import com.nmichail.pizza_shift_2025.presentation.util.toReadableSize
import com.nmichail.pizza_shift_2025.presentation.util.toReadableDough
import com.nmichail.pizza_shift_2025.presentation.util.toReadableTopping
import com.nmichail.pizza_shift_2025.presentation.util.toReadableSizeName

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CatalogDetailScreen(
    pizzaId: String,
    initialSize: String? = null,
    initialToppings: Set<String> = emptySet(),
    cartItemId: String? = null,
    onBack: () -> Unit,
    viewModel: PizzaDetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(pizzaId) {
        viewModel.loadPizza(pizzaId, initialSize, initialToppings, cartItemId)
    }

    val pizza = state.pizza
    var backPressed by remember { mutableStateOf(false) }
    val isEdit = state.cartItemId != null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 8.dp)
    ) {
        if (pizza == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 38.dp, start = 14.dp, end = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        if (!backPressed) {
                            backPressed = true
                            onBack()
                        }
                    },
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        //.background(MaterialTheme.colorScheme.surface)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_left),
                        contentDescription = "Назад",
                        tint = MaterialTheme.colorScheme.onTertiary
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Пицца",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
            
            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = pizza.imageUrl,
                    contentDescription = pizza.name,
                    modifier = Modifier
                        .size(220.dp)
                        .align(Alignment.TopCenter)
                        .padding(top = 8.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = pizza.name,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            val sizeDescription = buildString {
                val currentSize = state.selectedSize ?: pizza.sizes.firstOrNull()
                append(currentSize?.toReadableSize() ?: "error")
                if (pizza.doughs.isNotEmpty()) {
                    append(", ")
                    append(pizza.doughs.first().toReadableDough())
                }
            }
            Text(
                text = sizeDescription,
                color = MaterialTheme.colorScheme.onTertiary,
                fontSize = 15.sp,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = pizza.description ?: "",
                color = MaterialTheme.colorScheme.onTertiary,
                fontSize = 15.sp,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .background(MaterialTheme.colorScheme.onSecondary, shape = RoundedCornerShape(24.dp)),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                pizza.sizes.forEach { size ->
                    val selected = state.selectedSize == size
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(2.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (selected) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.onSecondary)
                            .clickable { 
                                viewModel.selectSize(size)
                            }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = size.toReadableSizeName(),
                            color = if (selected) MaterialTheme.colorScheme.onSurface else Color(0xFF6B7280),
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Добавить по вкусу",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                pizza.toppings.chunked(3).forEach { rowToppings ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        rowToppings.forEach { topping ->
                            val selected = state.selectedToppings.contains(topping.type)
                            ToppingCard(
                                topping = topping,
                                selected = selected,
                                onClick = { viewModel.toggleTopping(topping.type) }
                            )
                        }
                        repeat(3 - rowToppings.size) {
                            Spacer(modifier = Modifier.width(112.dp))
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = { 
                    if (!state.isAddedToCart) {
                        viewModel.addToCart(cartItemId = state.cartItemId)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (state.isAddedToCart || isEdit) Color.Gray else OrangePizza,
                    disabledContainerColor = Color.Gray
                ),
                enabled = !state.isAddedToCart
            ) {
                Text(
                    text = when {
                        state.isAddedToCart && isEdit -> "Отредактировано"
                        isEdit -> "Отредактировать"
                        state.isAddedToCart -> "Добавлено в корзину"
                        else -> "Добавить в корзину"
                    },
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}



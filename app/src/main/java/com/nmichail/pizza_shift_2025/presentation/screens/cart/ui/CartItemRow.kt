package com.nmichail.pizza_shift_2025.presentation.screens.cart.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nmichail.pizza_shift_2025.domain.entities.CartItem
import com.nmichail.pizza_shift_2025.presentation.theme.OrangePizza
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.navigation.NavController
import com.nmichail.pizza_shift_2025.presentation.util.toReadableTopping

@Composable
fun CartItemRow(
    cartItem: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit,
    onEdit: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = cartItem.pizza.imageUrl,
            contentDescription = cartItem.pizza.name,
            modifier = Modifier
                .size(66.dp)
                .clip(CircleShape)
                .background(Color(0xFFF3F3F6))
        )
        Spacer(modifier = Modifier.width(16.dp))
        CartItemInfoColumn(
            cartItem = cartItem,
            onIncrease = onIncrease,
            onDecrease = onDecrease,
            onEdit = onEdit
        )
        Spacer(modifier = Modifier.width(16.dp))
        CartItemPrice(price = cartItem.totalPrice)
    }
}

@Composable
fun CartItemInfoColumn(
    cartItem: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onEdit: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth(0.7f)) {
        Text(
            text = cartItem.pizza.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = translateDisplayName(cartItem.displayName),
            fontSize = 13.sp,
            color = Color.Gray,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(8.dp))
        CartItemActionsRow(
            count = cartItem.count,
            onIncrease = onIncrease,
            onDecrease = onDecrease,
            onEdit = onEdit
        )
    }
}

@Composable
fun CartItemActionsRow(
    count: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onEdit: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        MinusPlusSelectorCart(
            count = count,
            onIncrease = onIncrease,
            onDecrease = onDecrease,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "Изменить",
            color = Color(0xFFBDBDBD),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .padding(start = 8.dp)
                .clickable { onEdit() }
        )
    }
}

@Composable
fun CartItemPrice(price: Int) {
    Text(
        text = "$price ₽",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
}

fun translateDisplayName(displayName: String): String {
    val regex = "[A-Z_]+".toRegex()
    return displayName.replace(regex) { matchResult ->
        matchResult.value.toReadableTopping()
    }
}
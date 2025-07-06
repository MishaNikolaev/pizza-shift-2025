package com.nmichail.pizza_shift_2025.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.nmichail.pizza_shift_2025.R

enum class BottomBarTab {
    PIZZA, ORDERS, CART, PROFILE
}

@Composable
fun BottomBar(
    currentTab: BottomBarTab,
    onTabSelected: (BottomBarTab) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(bottom = 40.dp)
            .zIndex(1f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BottomBarItem(
                tab = BottomBarTab.PIZZA,
                iconRes = R.drawable.catalog,
                label = "Пицца",
                isSelected = currentTab == BottomBarTab.PIZZA,
                onClick = { onTabSelected(BottomBarTab.PIZZA) }
            )

            BottomBarItem(
                tab = BottomBarTab.ORDERS,
                iconRes = R.drawable.zakazat,
                label = "Заказы",
                isSelected = currentTab == BottomBarTab.ORDERS,
                onClick = { onTabSelected(BottomBarTab.ORDERS) }
            )

            BottomBarItem(
                tab = BottomBarTab.CART,
                iconRes = R.drawable.mysorka,
                label = "Корзина",
                isSelected = currentTab == BottomBarTab.CART,
                onClick = { onTabSelected(BottomBarTab.CART) }
            )

            BottomBarItem(
                tab = BottomBarTab.PROFILE,
                iconRes = R.drawable.person,
                label = "Профиль",
                isSelected = currentTab == BottomBarTab.PROFILE,
                onClick = { onTabSelected(BottomBarTab.PROFILE) }
            )
        }
    }
}

@Composable
fun BottomBarItem(
    tab: BottomBarTab,
    iconRes: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = label,
            tint = if (isSelected) Color(0xFFFF6B35) else Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) Color(0xFFFF6B35) else Color.Gray
        )
    }
} 
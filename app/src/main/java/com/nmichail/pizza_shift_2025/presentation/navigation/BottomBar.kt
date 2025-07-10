package com.nmichail.pizza_shift_2025.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.nmichail.pizza_shift_2025.presentation.navigation.BottomBarItem
import com.nmichail.pizza_shift_2025.presentation.navigation.BottomBarItemData
import com.nmichail.pizza_shift_2025.presentation.navigation.BottomBarTab
import com.nmichail.pizza_shift_2025.presentation.theme.OrangeAlmostPizza

val bottomBarItems = listOf(
    BottomBarItemData(BottomBarTab.PIZZA, R.drawable.catalog, "Пицца"),
    BottomBarItemData(BottomBarTab.ORDERS, R.drawable.zakazat, "Заказы"),
    BottomBarItemData(BottomBarTab.CART, R.drawable.mysorka, "Корзина"),
    BottomBarItemData(BottomBarTab.PROFILE, R.drawable.person, "Профиль")
)

@Composable
fun BottomBar(
    currentTab: BottomBarTab,
    onTabSelected: (BottomBarTab) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(bottom = 40.dp)
            .zIndex(1f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            bottomBarItems.forEach { item ->
                BottomBarItem(
                    tab = item.tab,
                    iconRes = item.iconRes,
                    label = item.label,
                    isSelected = currentTab == item.tab,
                    onClick = { onTabSelected(item.tab) }
                )
            }
        }
    }
}
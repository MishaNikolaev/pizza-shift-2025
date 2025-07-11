package com.nmichail.pizza_shift_2025.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.nmichail.pizza_shift_2025.R
import com.nmichail.pizza_shift_2025.presentation.navigation.BottomBarItem
import com.nmichail.pizza_shift_2025.presentation.navigation.BottomBarItemData
import com.nmichail.pizza_shift_2025.presentation.navigation.BottomBarTab

val bottomBarItems = listOf(
    BottomBarItemData(BottomBarTab.PIZZA, R.drawable.catalog, R.string.tab_pizza),
    BottomBarItemData(BottomBarTab.ORDERS, R.drawable.zakazat, R.string.tab_orders),
    BottomBarItemData(BottomBarTab.CART, R.drawable.mysorka, R.string.tab_cart),
    BottomBarItemData(BottomBarTab.PROFILE, R.drawable.person, R.string.tab_profile)
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
                    label = stringResource(item.label),
                    isSelected = currentTab == item.tab,
                    onClick = { onTabSelected(item.tab) }
                )
            }
        }
    }
}
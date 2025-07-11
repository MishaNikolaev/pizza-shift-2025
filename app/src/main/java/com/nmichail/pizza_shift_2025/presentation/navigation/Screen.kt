package com.nmichail.pizza_shift_2025.presentation.navigation

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object Catalog : Screen("catalog")
    object CatalogDetail : Screen("catalog_detail/{pizzaId}?size={size}&toppings={toppings}&cartItemId={cartItemId}") {
        fun createRoute(pizzaId: String, size: String? = null, toppings: Set<String>? = null, cartItemId: String? = null): String {
            val sizePart = size?.let { "&size=$it" } ?: ""
            val toppingsPart = toppings?.takeIf { it.isNotEmpty() }?.joinToString(",")?.let { "&toppings=$it" } ?: ""
            val cartItemIdPart = cartItemId?.let { "&cartItemId=$it" } ?: ""
            return "catalog_detail/$pizzaId?${sizePart.removePrefix("&")}${if (sizePart.isNotEmpty() && toppingsPart.isNotEmpty()) "&" else ""}${toppingsPart.removePrefix("&")}${cartItemIdPart}".removeSuffix("?")
        }
    }
    object Orders : Screen("orders")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object Payment : Screen("payment")
    object CardPayment : Screen("card_payment")
    object Successful : Screen("successful")
    object OrderDetails : Screen("order_details/{orderId}") {
        fun createRoute(orderId: String) = "order_details/$orderId"
    }
}
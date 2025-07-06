package com.nmichail.pizza_shift_2025.presentation.navigation

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object Main : Screen("main")
    object PizzaDetail : Screen("pizza_detail/{pizzaId}") {
        fun createRoute(pizzaId: String) = "pizza_detail/$pizzaId"
    }
}
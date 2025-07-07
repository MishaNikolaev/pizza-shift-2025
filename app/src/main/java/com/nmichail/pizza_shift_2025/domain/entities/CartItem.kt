package com.nmichail.pizza_shift_2025.domain.entities

data class CartItem(
    val id: String,
    val pizza: Pizza,
    val selectedSize: String,
    val selectedToppings: Set<String>,
    val count: Int = 1
) {
    val totalPrice: Int
        get() {
            val basePrice = pizza.sizePrices[selectedSize] ?: pizza.price
            val toppingsPrice = selectedToppings.sumOf { toppingType ->
                pizza.toppings.find { it.type == toppingType }?.price ?: 0
            }
            return ((basePrice + toppingsPrice) * count)
        }
    
    val displayName: String
        get() {
            val sizeName = when (selectedSize?.lowercase()) {
                "small" -> "Маленькая 25 см"
                "medium" -> "Средняя 30 см"
                "large" -> "Большая 35 см"
                else -> selectedSize ?: ""
            }
            val doughName = pizza.doughs.firstOrNull()?.let { 
                when (it) {
                    "thin" -> ", тонкое тесто"
                    "thick" -> ", толстое тесто"
                    else -> ""
                }
            } ?: ""
            
            val toppingsNames = selectedToppings.mapNotNull { toppingType ->
                pizza.toppings.find { it.type == toppingType }?.type?.let { type ->
                    when (type) {
                        "mozzarella" -> "моцарелла"
                        "pepperoni" -> "пепперони"
                        "mushrooms" -> "грибы"
                        "bell_peppers" -> "болгарский перец"
                        "onions" -> "лук"
                        "olives" -> "оливки"
                        "jalapeno" -> "халапеньо"
                        "bacon" -> "бекон"
                        "ham" -> "ветчина"
                        "pineapple" -> "ананас"
                        else -> type
                    }
                }
            }
            
            val toppingsText = if (toppingsNames.isNotEmpty()) {
                " + ${toppingsNames.joinToString(", ")}"
            } else ""
            
            return "$sizeName$doughName$toppingsText"
        }
} 
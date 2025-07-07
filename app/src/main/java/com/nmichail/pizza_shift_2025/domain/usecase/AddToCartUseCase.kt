package com.nmichail.pizza_shift_2025.domain.usecase

import com.nmichail.pizza_shift_2025.domain.entities.CartItem
import com.nmichail.pizza_shift_2025.domain.entities.Pizza
import com.nmichail.pizza_shift_2025.domain.repository.CartRepository
import java.util.UUID
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(
        pizza: Pizza,
        selectedSize: String,
        selectedToppings: Set<String>,
        cartItemId: String? = null
    ) {
        val cartItem = CartItem(
            id = cartItemId ?: UUID.randomUUID().toString(),
            pizza = pizza,
            selectedSize = selectedSize,
            selectedToppings = selectedToppings
        )
        cartRepository.addToCart(cartItem, replace = cartItemId != null)
    }
} 
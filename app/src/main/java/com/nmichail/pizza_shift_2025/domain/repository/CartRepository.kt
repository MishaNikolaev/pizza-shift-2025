package com.nmichail.pizza_shift_2025.domain.repository

import com.nmichail.pizza_shift_2025.domain.entities.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(): Flow<List<CartItem>>
    suspend fun addToCart(cartItem: CartItem, replace: Boolean = false)
    suspend fun removeFromCart(cartItemId: String)
    suspend fun updateCount(cartItemId: String, count: Int)
}
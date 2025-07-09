package com.nmichail.pizza_shift_2025.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nmichail.pizza_shift_2025.domain.entities.CartItem
import com.nmichail.pizza_shift_2025.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val prefs: SharedPreferences,
    private val gson: Gson
) : CartRepository {
    
    companion object {
        private const val KEY_CART_ITEMS = "cart_items"
    }
    
    private val _cartItems = MutableStateFlow<List<CartItem>>(loadCartItems())
    
    override fun getCartItems(): Flow<List<CartItem>> = _cartItems.asStateFlow()
    
    override fun addToCart(cartItem: CartItem, replace: Boolean) {
        val currentItems = _cartItems.value.toMutableList()
        if (replace) {
            currentItems.add(cartItem)
        } else {
        val existingIndex = currentItems.indexOfFirst { item ->
            item.pizza.id == cartItem.pizza.id &&
            item.selectedSize == cartItem.selectedSize &&
            item.selectedToppings == cartItem.selectedToppings
        }
        if (existingIndex != -1) {
            val existingItem = currentItems[existingIndex]
            currentItems[existingIndex] = existingItem.copy(count = existingItem.count + 1)
        } else {
            currentItems.add(cartItem)
            }
        }
        _cartItems.value = currentItems
        saveCartItems(currentItems)
    }
    
    override fun removeFromCart(cartItemId: String) {
        val currentItems = _cartItems.value.toMutableList()
        currentItems.removeAll { it.id == cartItemId }
        _cartItems.value = currentItems
        saveCartItems(currentItems)
    }
    
    override fun updateCount(cartItemId: String, count: Int) {
        val currentItems = _cartItems.value.toMutableList()
        val index = currentItems.indexOfFirst { it.id == cartItemId }
        if (index != -1) {
            if (count <= 0) {
                currentItems.removeAt(index)
            } else {
                currentItems[index] = currentItems[index].copy(count = count)
            }
            _cartItems.value = currentItems
            saveCartItems(currentItems)
        }
    }
    
    override fun clearCart() {
        _cartItems.value = emptyList()
        saveCartItems(emptyList())
    }
    
    private fun loadCartItems(): List<CartItem> {
        val json = prefs.getString(KEY_CART_ITEMS, "[]")
        val type = object : TypeToken<List<CartItem>>() {}.type
        return try {
            gson.fromJson<List<CartItem>>(json, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    private fun saveCartItems(items: List<CartItem>) {
        val json = gson.toJson(items)
        prefs.edit().putString(KEY_CART_ITEMS, json).apply()
    }
} 
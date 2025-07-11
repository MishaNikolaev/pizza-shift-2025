package com.nmichail.pizza_shift_2025.domain.repository

import com.nmichail.pizza_shift_2025.data.dto.PizzaOrderDto

interface OrderRepository {
    suspend fun getOrders(token: String): List<PizzaOrderDto>
    suspend fun getOrder(orderId: String, token: String): PizzaOrderDto
    suspend fun cancelOrder(orderId: String, token: String): Boolean
}
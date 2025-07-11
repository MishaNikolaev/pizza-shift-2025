package com.nmichail.pizza_shift_2025.data.repository

import com.nmichail.pizza_shift_2025.data.dto.CancelOrderRequest
import com.nmichail.pizza_shift_2025.data.dto.PizzaOrderDto
import com.nmichail.pizza_shift_2025.data.remote.OrderApi
import com.nmichail.pizza_shift_2025.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val api: OrderApi
) : OrderRepository {

    override suspend fun getOrders(token: String): List<PizzaOrderDto> {
        val response = api.getOrders(token)
        return if (response.success) response.orders else emptyList()
    }

    override suspend fun getOrder(orderId: String, token: String): PizzaOrderDto {
        val response = api.getOrder(orderId, token)
        return response.order
    }

    override suspend fun cancelOrder(orderId: String, token: String): Boolean {
        val response = api.cancelOrder(token, CancelOrderRequest(orderId))
        return response.success
    }
} 
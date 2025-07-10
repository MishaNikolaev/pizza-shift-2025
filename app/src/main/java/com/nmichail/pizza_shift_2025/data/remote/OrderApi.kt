package com.nmichail.pizza_shift_2025.data.remote

import com.nmichail.pizza_shift_2025.data.dto.BaseResponse
import com.nmichail.pizza_shift_2025.data.dto.CancelOrderRequest
import com.nmichail.pizza_shift_2025.data.dto.PizzaOrderResponse
import com.nmichail.pizza_shift_2025.data.dto.PizzaOrdersResponse
import retrofit2.http.*

interface OrderApi {
    @GET("pizza/orders")
    suspend fun getOrders(@Header("authorization") token: String): PizzaOrdersResponse

    @GET("pizza/orders/{orderId}")
    suspend fun getOrder(@Path("orderId") orderId: String, @Header("authorization") token: String): PizzaOrderResponse

    @PUT("pizza/orders/cancel")
    suspend fun cancelOrder(
        @Header("authorization") token: String,
        @Body body: CancelOrderRequest
    ): BaseResponse
} 
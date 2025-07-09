package com.nmichail.pizza_shift_2025.data.remote

import com.nmichail.pizza_shift_2025.data.dto.PizzaCatalogDto
import com.nmichail.pizza_shift_2025.data.dto.PaymentRequestDto
import com.nmichail.pizza_shift_2025.data.dto.PaymentResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PizzaApi {
    @GET("pizza/catalog")
    suspend fun getCatalog(): PizzaCatalogDto

    @POST("pizza/payment")
    suspend fun payForOrder(@Body body: PaymentRequestDto): PaymentResponseDto
} 
package com.nmichail.pizza_shift_2025.data.remote

import com.nmichail.pizza_shift_2025.data.dto.PizzaCatalogDto
import retrofit2.http.GET

interface PizzaApi {
    @GET("pizza/catalog")
    suspend fun getCatalog(): PizzaCatalogDto
} 
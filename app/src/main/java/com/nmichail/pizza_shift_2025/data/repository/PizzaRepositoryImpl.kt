package com.nmichail.pizza_shift_2025.data.repository

import android.util.Log
import com.nmichail.pizza_shift_2025.data.remote.PizzaApi
import com.nmichail.pizza_shift_2025.data.dto.PizzaDto
import com.nmichail.pizza_shift_2025.data.dto.PizzaCatalogDto
import com.nmichail.pizza_shift_2025.data.dto.SizeDto
import com.nmichail.pizza_shift_2025.data.dto.ToppingDto
import com.nmichail.pizza_shift_2025.domain.entities.Pizza
import com.nmichail.pizza_shift_2025.domain.entities.PizzaIngredient
import com.nmichail.pizza_shift_2025.domain.entities.PizzaSize
import com.nmichail.pizza_shift_2025.domain.entities.PizzaDough
import com.nmichail.pizza_shift_2025.domain.entities.PizzaTopping
import com.nmichail.pizza_shift_2025.domain.repository.PizzaRepository
import javax.inject.Inject

class PizzaRepositoryImpl @Inject constructor(
    private val api: PizzaApi,
    private val imageBaseUrl: String
) : PizzaRepository {
    override suspend fun getCatalog(): List<Pizza> {
        val dto = api.getCatalog()
        if (!dto.success) throw Exception(dto.reason ?: "Unknown error")
        return dto.catalog.map { it.toDomain() }
    }

    private fun ToppingDto.toDomain(): PizzaTopping = PizzaTopping(
        type = type,
        price = price,
        img = if (img != null) {
            if (img.startsWith("http")) img else "$imageBaseUrl$img"
        } else {
            null
        }
    )

    private fun PizzaDto.toDomain(): Pizza = Pizza(
        id = id,
        name = name,
        description = description,
        imageUrl = if (img != null) {
            if (img.startsWith("http")) img else "$imageBaseUrl$img"
        } else {
            null
        },
        price = sizes.minByOrNull { it.price }?.price ?: 0,
        sizes = sizes.map { it.type },
        sizePrices = sizes.associate { it.type to it.price },
        toppings = toppings.map { it.toDomain() },
        doughs = doughs.map { it.type }
    )
} 
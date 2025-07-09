package com.nmichail.pizza_shift_2025.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.nmichail.pizza_shift_2025.data.dto.PaymentRequestDto
import com.nmichail.pizza_shift_2025.data.dto.PaymentResponseDto
import com.nmichail.pizza_shift_2025.data.remote.PizzaApi
import com.nmichail.pizza_shift_2025.data.dto.PizzaOrderDto
import com.nmichail.pizza_shift_2025.domain.repository.PaymentRepository
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val api: PizzaApi,
    private val prefs: SharedPreferences,
    private val gson: Gson
) : PaymentRepository {
    companion object {
        private const val KEY_LAST_ORDER = "last_order"
    }
    override suspend fun payForOrder(request: PaymentRequestDto): PaymentResponseDto {
        val response = api.payForOrder(request)
        saveLastOrder(response.order)
        return response
    }

    private fun saveLastOrder(order: PizzaOrderDto) {
        val json = gson.toJson(order)
        prefs.edit().putString(KEY_LAST_ORDER, json).apply()
    }

    override fun getLastOrder(): PizzaOrderDto? {
        val json = prefs.getString(KEY_LAST_ORDER, null) ?: return null
        return try {
            gson.fromJson(json, PizzaOrderDto::class.java)
        } catch (e: Exception) {
            null
        }
    }
} 
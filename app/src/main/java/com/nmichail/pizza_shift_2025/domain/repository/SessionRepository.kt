package com.nmichail.pizza_shift_2025.domain.repository

interface SessionRepository {

    suspend fun isAuthorized(): Boolean

    fun setToken(token: String?)

    fun getToken(): String?

}
package com.nmichail.pizza_shift_2025.presentation.screens.auth

sealed class AuthScreenState {
    object EnterPhone : AuthScreenState()
    data class EnterCode(val phone: String, val secondsLeft: Int = 0) : AuthScreenState()
}
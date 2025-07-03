package com.nmichail.pizza_shift_2025.presentation.util

sealed class Result<out T> {

    data class Success<out T>(val value: T) : Result<T>()

    data class Error(val reason: String) : Result<Nothing>()

} 
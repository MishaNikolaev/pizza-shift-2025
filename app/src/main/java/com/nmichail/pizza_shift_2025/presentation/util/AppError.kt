package com.nmichail.pizza_shift_2025.presentation.util

enum class ErrorType {
    NETWORK_ERROR,
    BAD_REQUEST,
    UNAUTHORIZED,
    SERVER_ERROR,
    UNKNOWN
}

sealed class AppError(
    val type: ErrorType,
    open val message: String? = null
) {
    object NetworkError : AppError(ErrorType.NETWORK_ERROR)
    object BadRequest : AppError(ErrorType.BAD_REQUEST)
    object Unauthorized : AppError(ErrorType.UNAUTHORIZED)
    data class ServerError(val code: Int, override val message: String?) : AppError(ErrorType.SERVER_ERROR, message)
    data class UnknownError(override val message: String?) : AppError(ErrorType.UNKNOWN, message)
} 
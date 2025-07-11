package com.nmichail.pizza_shift_2025.presentation.util

import retrofit2.HttpException
import java.io.IOException

object ErrorHandler {
    fun parseError(throwable: Throwable): AppError {
        return when (throwable) {
            is HttpException -> {
                when (throwable.code()) {
                    400 -> AppError.BadRequest
                    401 -> AppError.Unauthorized
                    500 -> AppError.ServerError(500, throwable.message())
                    else -> AppError.ServerError(throwable.code(), throwable.message())
                }
            }
            is IOException -> AppError.NetworkError
            else -> AppError.UnknownError(throwable.message)
        }
    }
    
    fun parseErrorFromString(errorMessage: String): AppError {
        return when {
            errorMessage.contains("400") || errorMessage.contains("Bad Request") -> AppError.BadRequest
            errorMessage.contains("401") || errorMessage.contains("Unauthorized") -> AppError.Unauthorized
            errorMessage.contains("500") || errorMessage.contains("Server Error") -> AppError.ServerError(500, errorMessage)
            else -> AppError.UnknownError(errorMessage)
        }
    }
} 
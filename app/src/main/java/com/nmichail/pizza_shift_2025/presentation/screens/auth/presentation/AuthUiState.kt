package com.nmichail.pizza_shift_2025.presentation.screens.auth.presentation

sealed class AuthUiState {
    object Loading : AuthUiState()

    data class EnterPhone(
        val phone: String = "",
        val otpState: OtpState = OtpState.None
    ) : AuthUiState()

    data class EnterCode(
        val phone: String,
        val code: String = "",
        val secondsLeft: Int = 0,
        val signInState: SignInState = SignInState.None
    ) : AuthUiState()
}

sealed class OtpState {
    object None : OtpState()
    object Loading : OtpState()
    data class Error(val message: String) : OtpState()
}

sealed class SignInState {
    object None : SignInState()
    object Loading : SignInState()
    data class Error(val message: String) : SignInState()
} 
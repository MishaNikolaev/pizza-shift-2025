package com.nmichail.pizza_shift_2025.presentation.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmichail.pizza_shift_2025.domain.usecase.GetAuthorizedUseCase
import com.nmichail.pizza_shift_2025.domain.usecase.GetIsAuthorizedUseCase
import com.nmichail.pizza_shift_2025.domain.usecase.GetOtpUseCase
import com.nmichail.pizza_shift_2025.domain.usecase.GetSignInUseCase
import com.nmichail.pizza_shift_2025.presentation.screens.auth.components.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val requestOtpUseCase: GetOtpUseCase,
    private val signInUseCase: GetSignInUseCase,
    private val setAuthorizedUseCase: GetAuthorizedUseCase,
    private val isAuthorizedUseCase: GetIsAuthorizedUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    private val _isAuthorized = MutableStateFlow(false)
    val isAuthorized: StateFlow<Boolean> = _isAuthorized

    fun onPhoneChanged(phone: String) {
        _uiState.value = _uiState.value.copy(phone = phone, error = null)
    }

    fun onCodeChanged(code: String) {
        _uiState.value = _uiState.value.copy(code = code, error = null)
    }

    fun onContinueClicked() {
        val phone = _uiState.value.phone.trim()
        if (phone.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "Введите номер телефона")
            return
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val result = requestOtpUseCase(phone)
            if (result.isSuccess) {
                startTimer(60)
                _uiState.value = _uiState.value.copy(
                    screenState = AuthScreenState.EnterCode(phone, 60),
                    isLoading = false,
                    code = ""
                )
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, error = result.error)
            }
        }
    }

    fun onSignInClicked() {
        val phone = _uiState.value.phone.trim()
        val code = _uiState.value.code.trim()
        if (code.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "Введите проверочный код")
            return
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val result = signInUseCase(phone, code)
            if (result.isSuccess) {
                _uiState.value = _uiState.value.copy(isLoading = false)
                setAuthorizedUseCase(true)
                _isAuthorized.value = true
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, error = result.error)
            }
        }
    }

    fun onResendCodeClicked() {
        val phone = _uiState.value.phone.trim()
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val result = requestOtpUseCase(phone)
            if (result.isSuccess) {
                startTimer(60)
                _uiState.value = _uiState.value.copy(
                    screenState = AuthScreenState.EnterCode(phone, 60),
                    isLoading = false,
                    code = ""
                )
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, error = result.error)
            }
        }
    }

    private fun startTimer(seconds: Int) {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            for (sec in (seconds - 1) downTo 0) {
                val state = _uiState.value.screenState
                if (state is AuthScreenState.EnterCode) {
                    _uiState.value = _uiState.value.copy(
                        screenState = state.copy(secondsLeft = sec)
                    )
                }
                delay(1000)
            }
        }
    }

    fun checkAuthorization() {
        viewModelScope.launch {
            val authorized = isAuthorizedUseCase()
            _isAuthorized.value = authorized
        }
    }
} 
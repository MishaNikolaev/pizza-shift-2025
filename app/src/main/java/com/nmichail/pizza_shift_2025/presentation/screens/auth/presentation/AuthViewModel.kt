package com.nmichail.pizza_shift_2025.presentation.screens.auth.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmichail.pizza_shift_2025.data.repository.AuthRepositoryImpl
import com.nmichail.pizza_shift_2025.domain.usecase.GetAuthorizedUseCase
import com.nmichail.pizza_shift_2025.domain.usecase.RequestOtpUseCase
import com.nmichail.pizza_shift_2025.domain.usecase.SignInUseCase
import com.nmichail.pizza_shift_2025.presentation.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val requestOtpUseCase: RequestOtpUseCase,
    private val signInUseCase: SignInUseCase,
    private val getAuthorizedUseCase: GetAuthorizedUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val authRepositoryImpl: AuthRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.EnterPhone())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    private val _isAuthorized = MutableStateFlow(false)
    val isAuthorized: StateFlow<Boolean> = _isAuthorized

    private val _isAuthCheckFinished = MutableStateFlow(false)
    val isAuthCheckFinished = _isAuthCheckFinished.asStateFlow()

    val authorizedPhoneFlow = authRepositoryImpl.phoneFlow.stateIn(viewModelScope, SharingStarted.Lazily, null)

    companion object {
        private const val KEY_LAST_AUTH_PHONE = "last_authorized_phone"
    }

    private val currentEnterPhone: AuthUiState.EnterPhone?
        get() = _uiState.value as? AuthUiState.EnterPhone

    private val currentEnterCode: AuthUiState.EnterCode?
        get() = _uiState.value as? AuthUiState.EnterCode

    fun onPhoneChanged(phone: String) {
        val current = currentEnterPhone ?: return
        _uiState.value = current.copy(
            phone = phone,
            otpState = OtpState.None
        )
    }

    fun onCodeChanged(code: String) {
        val current = currentEnterCode ?: return
        _uiState.value = current.copy(
            code = code,
            signInState = SignInState.None
        )
    }

    fun onContinueClicked() {
        val current = currentEnterPhone ?: return
        val phone = current.phone.trim()
        if (phone.isBlank()) {
            _uiState.value = current.copy(otpState = OtpState.Error("Введите номер телефона"))
            return
        }
        viewModelScope.launch {
            _uiState.value = current.copy(otpState = OtpState.Loading)
            when (val result = requestOtpUseCase(phone)) {
                is Result.Success -> {
                    _uiState.value = AuthUiState.EnterCode(phone = phone, code = "", secondsLeft = 60)
                    startTimer(60)
                }

                is Result.Error -> {
                    _uiState.value = current.copy(otpState = OtpState.Error(result.reason))
                }
            }
        }
    }

    fun onSignInClicked() {
        val current = currentEnterCode ?: return
        val phone = current.phone.trim()
        val code = current.code.trim()
        if (code.isBlank()) {
            _uiState.value =
                current.copy(signInState = SignInState.Error("Введите проверочный код", "Введите проверочный код"))
            return
        }
        viewModelScope.launch {
            _uiState.value = current.copy(signInState = SignInState.Loading)
            when (val result = signInUseCase(phone, code)) {
                is Result.Success -> {
                    savedStateHandle.set(KEY_LAST_AUTH_PHONE, phone)
                    authRepositoryImpl.savePhoneToDataStore(phone)
                    _uiState.value = AuthUiState.EnterPhone(phone = phone)
                    _isAuthorized.value = true
                }

                is Result.Error -> {
                    _uiState.value = current.copy(signInState = SignInState.Error(result.reason, result.reason))
                }
            }
        }
    }

    fun onResendCodeClicked() {
        val current = currentEnterCode ?: return
        val phone = current.phone.trim()
        viewModelScope.launch {
            _uiState.value = current.copy(signInState = SignInState.Loading)
            when (val result = requestOtpUseCase(phone)) {
                is Result.Success -> {
                    _uiState.value = current.copy(code = "", secondsLeft = 60, signInState = SignInState.None)
                    startTimer(60)
                }

                is Result.Error -> {
                    _uiState.value = current.copy(signInState = SignInState.Error(result.reason, result.reason))
                }
            }
        }
    }

    private fun startTimer(seconds: Int) {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            for (sec in (seconds - 1) downTo 0) {
                val current = _uiState.value as? AuthUiState.EnterCode ?: return@launch
                _uiState.value = current.copy(secondsLeft = sec)
                delay(1000)
            }
        }
    }

    fun checkAuthorization() {
        viewModelScope.launch {
            val authorized = getAuthorizedUseCase()
            _isAuthorized.value = authorized
            _isAuthCheckFinished.value = true
        }
    }

    suspend fun getAuthorizedPhone(): String? {
        val fromDS = authRepositoryImpl.getPhoneFromDataStore()
        return fromDS
    }
} 
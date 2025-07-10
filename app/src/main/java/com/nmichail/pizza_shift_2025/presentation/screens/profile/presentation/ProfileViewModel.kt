package com.nmichail.pizza_shift_2025.presentation.screens.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmichail.pizza_shift_2025.data.dto.UserDto
import com.nmichail.pizza_shift_2025.domain.repository.SessionRepository
import com.nmichail.pizza_shift_2025.domain.usecase.GetProfileUseCase
import com.nmichail.pizza_shift_2025.domain.usecase.UpdateProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val sessionRepository: SessionRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private var token: String? = null

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            token = sessionRepository.getToken()?.let { "Bearer $it" }
            if (token == null) {
                _uiState.value = ProfileUiState.Error("Не авторизован")
                return@launch
            }
            val user = getProfileUseCase(token!!)
            if (user != null) {
                _uiState.value = ProfileUiState.View(user)
            } else {
                _uiState.value = ProfileUiState.Error("Ошибка загрузки профиля")
            }
        }
    }

    fun startEdit() {
        val current = _uiState.value
        if (current is ProfileUiState.View) {
            _uiState.value = ProfileUiState.Edit(current.user)
        }
    }

    fun updateProfile(user: UserDto) {
        viewModelScope.launch {
            if (token == null) {
                _uiState.value = ProfileUiState.Error("Не авторизован")
                return@launch
            }
            _uiState.value = ProfileUiState.Loading
            val success = updateProfileUseCase(token!!, user)
            if (success) {
                _uiState.value = ProfileUiState.View(user)
            } else {
                _uiState.value = ProfileUiState.Error("Ошибка обновления профиля")
            }
        }
    }

    fun cancelEdit() {
        loadProfile()
    }

    fun logout(onLogout: () -> Unit) {
        sessionRepository.setToken(null)
        onLogout()
    }
}
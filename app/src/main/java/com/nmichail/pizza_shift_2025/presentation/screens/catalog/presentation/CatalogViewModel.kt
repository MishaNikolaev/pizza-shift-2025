package com.nmichail.pizza_shift_2025.presentation.screens.catalog.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmichail.pizza_shift_2025.domain.usecase.GetPizzaCatalogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getPizzaCatalogUseCase: GetPizzaCatalogUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<CatalogUiState>(CatalogUiState.Loading)
    val uiState: StateFlow<CatalogUiState> = _uiState

    init {
        loadCatalog()
    }

    fun loadCatalog() {
        _uiState.value = CatalogUiState.Loading
        viewModelScope.launch {
            try {
                val pizzas = getPizzaCatalogUseCase()
                _uiState.value = CatalogUiState.Success(pizzas)
            } catch (e: Exception) {
                _uiState.value = CatalogUiState.Error(e.message ?: "Ошибка загрузки каталога")
            }
        }
    }
} 
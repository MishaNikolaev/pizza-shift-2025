package com.nmichail.pizza_shift_2025.presentation.screens.catalog.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nmichail.pizza_shift_2025.presentation.screens.catalog.presentation.CatalogUiState
import com.nmichail.pizza_shift_2025.presentation.screens.catalog.presentation.CatalogViewModel

@Composable
fun CatalogScreen(
    viewModel: CatalogViewModel = hiltViewModel(),
    onPizzaClick: (String) -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()
    when (state) {
        is CatalogUiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        is CatalogUiState.Error -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = (state as CatalogUiState.Error).message, color = MaterialTheme.colorScheme.error)
        }
        is CatalogUiState.Success -> {
            val pizzas = (state as CatalogUiState.Success).pizzas
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(vertical = 32.dp)
            ) {
                item {
                    Text(
                        text = "Пицца",
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(start = 25.dp, top = 12.dp, bottom = 16.dp)
                    )
                }
                
                items(pizzas) { pizza ->
                    PizzaCard(pizza = pizza, onClick = { onPizzaClick(pizza.id) })
                }
                
                item {
                    Spacer(modifier = Modifier.height(48.dp))
                }
            }
        }
    }
}
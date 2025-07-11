package com.nmichail.pizza_shift_2025.presentation.screens.payment.ui.paid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nmichail.pizza_shift_2025.presentation.screens.payment.presentation.PaymentViewModel

@Composable
fun SuccessfulScreen(
    onClose: () -> Unit = {},
    onOrderDetails: () -> Unit = {},
    onMain: () -> Unit = {},
    viewModel: PaymentViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) { viewModel.loadLastOrder() }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        SuccessfulCloseButton(
            onClose = onClose,
            modifier = Modifier.align(Alignment.TopEnd)
        )
        
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(horizontal = 24.dp)
                .padding(top = 48.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            
            SuccessfulHeader()
            SuccessfulOrderInfo(
                lastOrder = state.lastOrder,
                modifier = Modifier.align(Alignment.Start)
            )
            SuccessfulAddressInfo(
                lastOrder = state.lastOrder,
                modifier = Modifier.align(Alignment.Start)
            )
            SuccessfulPriceInfo(
                lastOrder = state.lastOrder,
                modifier = Modifier.align(Alignment.Start)
            )
            SuccessfulSmsInfo(
                modifier = Modifier.align(Alignment.Start)
            )
            SuccessfulActionButtons(
                onOrderDetails = onOrderDetails,
                onMain = onMain
            )
        }
    }
}
package com.nmichail.pizza_shift_2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nmichail.pizza_shift_2025.presentation.navigation.AppNavGraph
import com.nmichail.pizza_shift_2025.presentation.theme.Pizzashift2025Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pizzashift2025Theme(dynamicColor = false) {
                AppNavGraph()
            }
        }
    }
}
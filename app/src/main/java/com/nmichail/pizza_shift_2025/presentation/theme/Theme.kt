package com.nmichail.pizza_shift_2025.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = OrangePizza,
    secondary = OrangeAlmostPizza,
    tertiary = Pink80,
    background = Color(0xFF141C24),
    surface = Color(0xFF141C24),
    onPrimary = Color(0xFF344051),
    inversePrimary = Color.White,
    onSecondary =  Color(0xFF344051),
    onTertiary = Color(0xFFCED2DA),
    onBackground = Color.White,
    onSurface = Color.White,
    tertiaryContainer = Color(0xFF141C24),
    onSurfaceVariant = Color(0xFF344051),
    secondaryContainer =  Color(0xFF8C8C8C)
)

private val LightColorScheme = lightColorScheme(
    primary = OrangePizza,
    secondary = OrangeAlmostPizza,
    tertiary = Pink40,
    background = Color.White,
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    inversePrimary = Color.Black,
    onSecondary = Color.White,
    onTertiary = Color(0xFF344051),
    onBackground = Color.Black,
    onSurface = Color.Black,
    tertiaryContainer = Color(0xFFF3F4F6),
    onSurfaceVariant = Color(0xFFF5F6F8),
    secondaryContainer =  Color(0xFF8C8C8C)
    )

@Composable
fun Pizzashift2025Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun PizzaOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    textStyle: TextStyle = TextStyle.Default,
    placeholder: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        singleLine = singleLine,
        enabled = enabled,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        keyboardOptions = keyboardOptions,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedLabelColor = Color.Gray,
            unfocusedLabelColor = Color.Gray,
            cursorColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp)
    )
}

val PizzaButtonColors
    @Composable get() = ButtonDefaults.buttonColors(
    containerColor = OrangePizza,
    contentColor = Color.White,
    disabledContainerColor = OrangePizza.copy(alpha = 0.5f),
    disabledContentColor = Color.White.copy(alpha = 0.5f)
)

val PizzaTextFieldAllGrayColors
    @Composable get() = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color.Gray,
        unfocusedBorderColor = Color.Gray.copy(alpha = 0.7f),
        disabledBorderColor = Color.Gray,
        focusedLabelColor = Color.Gray,
        unfocusedLabelColor = Color.Gray.copy(alpha = 0.7f),
        cursorColor = Color.Gray,
        disabledTextColor = Color.Gray,
        disabledContainerColor = Color(0xFFF5F5F5),
        unfocusedContainerColor = Color(0xFFF5F5F5),
        focusedContainerColor = Color(0xFFF5F5F5)
    )
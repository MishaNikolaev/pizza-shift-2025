package com.nmichail.pizza_shift_2025.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import com.nmichail.pizza_shift_2025.presentation.theme.OrangePizza
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
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

val PizzaTextFieldColors
    @Composable get() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = OrangePizza,
    unfocusedBorderColor = OrangePizza.copy(alpha = 0.7f),
    disabledBorderColor = Color.Gray,
    focusedLabelColor = OrangePizza,
    unfocusedLabelColor = OrangePizza.copy(alpha = 0.7f),
    cursorColor = OrangePizza
)

val PizzaButtonColors
    @Composable get() = ButtonDefaults.buttonColors(
    containerColor = OrangePizza,
    contentColor = Color.White,
    disabledContainerColor = OrangePizza.copy(alpha = 0.5f),
    disabledContentColor = Color.White.copy(alpha = 0.5f)
)

val PizzaTextFieldGrayCursorColors
    @Composable get() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = OrangePizza,
    unfocusedBorderColor = OrangePizza.copy(alpha = 0.7f),
    disabledBorderColor = Color.Gray,
    focusedLabelColor = OrangePizza,
    unfocusedLabelColor = OrangePizza.copy(alpha = 0.7f),
    cursorColor = Color.Gray
)

val PizzaTextFieldGrayBgColors
    @Composable get() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = OrangePizza,
    unfocusedBorderColor = OrangePizza.copy(alpha = 0.7f),
    disabledBorderColor = Color.Gray,
    focusedLabelColor = OrangePizza,
    unfocusedLabelColor = OrangePizza.copy(alpha = 0.7f),
    cursorColor = Color.Gray,
    disabledTextColor = Color.Gray,
    disabledContainerColor = Color(0xFFF5F5F5), 
    unfocusedContainerColor = Color(0xFFF5F5F5),
    focusedContainerColor = Color(0xFFF5F5F5)
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
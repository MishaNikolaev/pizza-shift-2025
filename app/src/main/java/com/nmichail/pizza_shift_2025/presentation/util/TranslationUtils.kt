package com.nmichail.pizza_shift_2025.presentation.util

fun String.toReadableSize(): String {
    return when (this) {
        "SMALL" -> "25 см"
        "MEDIUM" -> "30 см"
        "LARGE" -> "35 см"
        else -> this
    }
}

fun String.toReadableSizeName(): String {
    return when (this) {
        "SMALL" -> "Маленькая"
        "MEDIUM" -> "Средняя"
        "LARGE" -> "Большая"
        else -> this
    }
}

fun String.toReadableDough(): String {
    return when (this) {
        "THIN" -> "тонкое тесто"
        "THICK" -> "традиционное тесто"
        else -> this
    }
}

fun String.toReadableTopping(): String {
    return when (this) {
        "PINEAPPLE" -> "Ананас"
        "GREEN_PEPPER" -> "Зеленый перец"
        "MUSHROOMS" -> "Грибы"
        "BACON" -> "Бекон"
        "SHRIMPS" -> "Креветки"
        "HAM" -> "Ветчина"
        "MOZZARELLA" -> "Моцарелла"
        "PEPERONI" -> "Пепперони"
        "CHICKEN_FILLET" -> "Куриная грудка"
        "ONION" -> "Лук"
        "BASIL" -> "Базилик"
        "CHILE" -> "Чили"
        "CHEDDAR" -> "Чеддер"
        "MEATBALLS" -> "Фрикадельки"
        "PICKLE" -> "Огурцы"
        "TOMATO" -> "Помидоры"
        "FETA" -> "Фета"
        "PARMESAN" -> "Пармезан"
        else -> this
    }
} 
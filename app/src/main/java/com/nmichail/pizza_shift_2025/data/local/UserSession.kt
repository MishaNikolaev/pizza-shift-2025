package com.nmichail.pizza_shift_2025.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_session")
data class UserSession(
    @PrimaryKey val id: Int = 0,
    val isAuthorized: Boolean
) 
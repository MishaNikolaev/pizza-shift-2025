package com.nmichail.pizza_shift_2025.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserSession::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userSessionDao(): UserSessionDao
} 
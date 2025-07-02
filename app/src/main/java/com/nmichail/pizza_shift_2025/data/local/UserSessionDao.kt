package com.nmichail.pizza_shift_2025.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserSessionDao {
    @Query("SELECT * FROM user_session WHERE id = 0")
    suspend fun getSession(): UserSession?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSession(session: UserSession)
} 
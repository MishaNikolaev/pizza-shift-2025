package com.nmichail.pizza_shift_2025.di

import android.content.Context
import androidx.room.Room
import com.nmichail.pizza_shift_2025.data.local.AppDatabase
import com.nmichail.pizza_shift_2025.data.local.UserSessionDao
import com.nmichail.pizza_shift_2025.domain.repository.SessionRepository
import com.nmichail.pizza_shift_2025.data.repository.SessionRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_db").build()

    @Provides
    fun provideUserSessionDao(db: AppDatabase): UserSessionDao = db.userSessionDao()

    @Provides
    @Singleton
    fun provideSessionRepository(impl: SessionRepositoryImpl): SessionRepository = impl
} 
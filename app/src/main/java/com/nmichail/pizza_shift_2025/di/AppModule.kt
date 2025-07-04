package com.nmichail.pizza_shift_2025.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.nmichail.pizza_shift_2025.data.remote.AuthApi
import com.nmichail.pizza_shift_2025.data.remote.PizzaApi
import com.nmichail.pizza_shift_2025.data.repository.AuthRepositoryImpl
import com.nmichail.pizza_shift_2025.data.repository.PizzaRepositoryImpl
import com.nmichail.pizza_shift_2025.data.repository.SessionRepositoryImpl
import com.nmichail.pizza_shift_2025.domain.repository.AuthRepository
import com.nmichail.pizza_shift_2025.domain.repository.PizzaRepository
import com.nmichail.pizza_shift_2025.domain.repository.SessionRepository
import com.nmichail.pizza_shift_2025.domain.usecase.GetPizzaCatalogUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://shift-intensive.ru/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi, sessionRepository: SessionRepository): AuthRepository =
        AuthRepositoryImpl(api, sessionRepository)

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        //Log.d("AppModule", "provideSharedPreferences: context=$context, name=session_prefs")
        return context.getSharedPreferences("session_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSessionRepository(
        prefs: SharedPreferences,
        api: AuthApi
    ): SessionRepository = SessionRepositoryImpl(prefs, api)

    @Provides
    @Singleton
    fun providePizzaApi(retrofit: Retrofit): PizzaApi =
        retrofit.create(PizzaApi::class.java)

    @Provides
    @Singleton
    fun providePizzaRepository(api: PizzaApi): PizzaRepository =
        PizzaRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideGetPizzaCatalogUseCase(repository: PizzaRepository): GetPizzaCatalogUseCase =
        GetPizzaCatalogUseCase(repository)
} 
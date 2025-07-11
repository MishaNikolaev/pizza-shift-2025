package com.nmichail.pizza_shift_2025.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.nmichail.pizza_shift_2025.data.remote.AuthApi
import com.nmichail.pizza_shift_2025.data.remote.OrderApi
import com.nmichail.pizza_shift_2025.data.remote.PizzaApi
import com.nmichail.pizza_shift_2025.data.remote.ProfileApi
import com.nmichail.pizza_shift_2025.data.repository.*
import com.nmichail.pizza_shift_2025.domain.repository.*
import com.nmichail.pizza_shift_2025.domain.usecase.AddToCartUseCase
import com.nmichail.pizza_shift_2025.domain.usecase.GetPizzaCatalogUseCase
import com.nmichail.pizza_shift_2025.domain.usecase.GetProfileUseCase
import com.nmichail.pizza_shift_2025.domain.usecase.UpdateProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
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
    fun provideAuthRepositoryImpl(
        api: AuthApi,
        sessionRepository: SessionRepository,
        @ApplicationContext context: Context
    ): AuthRepositoryImpl = AuthRepositoryImpl(api, sessionRepository, context)

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
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
    @Named("ImageBaseUrl")
    fun provideImageBaseUrl(): String = "https://shift-intensive.ru/api/"

    @Provides
    @Singleton
    fun providePizzaRepository(api: PizzaApi, @Named("ImageBaseUrl") imageBaseUrl: String): PizzaRepository =
        PizzaRepositoryImpl(api, imageBaseUrl)

    @Provides
    @Singleton
    fun providePaymentRepository(api: PizzaApi, prefs: SharedPreferences, gson:Gson): PaymentRepository =
        PaymentRepositoryImpl(api, prefs, gson)

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideCartRepository(prefs: SharedPreferences, gson: Gson): CartRepository =
        CartRepositoryImpl(prefs, gson)

    @Provides
    @Singleton
    fun provideGetPizzaCatalogUseCase(repository: PizzaRepository): GetPizzaCatalogUseCase =
        GetPizzaCatalogUseCase(repository)

    @Provides
    @Singleton
    fun provideAddToCartUseCase(repository: CartRepository): AddToCartUseCase =
        AddToCartUseCase(repository)

    @Provides
    @Singleton
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository = authRepositoryImpl

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideOrderApi(retrofit: Retrofit): OrderApi {
        return retrofit.create(OrderApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileApi(retrofit: Retrofit): ProfileApi =
        retrofit.create(ProfileApi::class.java)

    @Provides
    @Singleton
    fun provideProfileRepository(profileApi: ProfileApi, authApi: AuthApi): ProfileRepository =
        ProfileRepositoryImpl(profileApi, authApi)

    @Provides
    @Singleton
    fun provideGetProfileUseCase(repository: ProfileRepository): GetProfileUseCase =
        GetProfileUseCase(repository)

    @Provides
    @Singleton
    fun provideUpdateProfileUseCase(repository: ProfileRepository): UpdateProfileUseCase =
        UpdateProfileUseCase(repository)
} 
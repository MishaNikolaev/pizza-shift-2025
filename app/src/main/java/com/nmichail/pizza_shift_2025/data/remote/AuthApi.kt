package com.nmichail.pizza_shift_2025.data.remote

import com.nmichail.pizza_shift_2025.data.dto.OtpRequestDto
import com.nmichail.pizza_shift_2025.data.dto.OtpResponseDto
import com.nmichail.pizza_shift_2025.data.dto.SignInRequestDto
import com.nmichail.pizza_shift_2025.data.dto.SignInResponseDto
import com.nmichail.pizza_shift_2025.data.dto.SessionResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/otp")
    suspend fun requestOtp(@Body body: OtpRequestDto): OtpResponseDto

    @POST("users/signin")
    suspend fun signIn(@Body body: SignInRequestDto): SignInResponseDto

    @GET("users/session")
    suspend fun getSession(@Header("Authorization") authorization: String): SessionResponse
} 
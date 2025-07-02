package com.nmichail.pizza_shift_2025.data.remote

import com.nmichail.pizza_shift_2025.data.dto.OtpRequestDto
import com.nmichail.pizza_shift_2025.data.dto.OtpResponseDto
import com.nmichail.pizza_shift_2025.data.dto.SignInRequestDto
import com.nmichail.pizza_shift_2025.data.dto.SignInResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/otp")
    suspend fun requestOtp(@Body body: OtpRequestDto): OtpResponseDto

    @POST("users/signin")
    suspend fun signIn(@Body body: SignInRequestDto): SignInResponseDto
} 
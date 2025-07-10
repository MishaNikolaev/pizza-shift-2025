package com.nmichail.pizza_shift_2025.data.repository

import com.nmichail.pizza_shift_2025.data.dto.UserDto
import com.nmichail.pizza_shift_2025.data.dto.UpdateProfileRequestDto
import com.nmichail.pizza_shift_2025.data.dto.UpdateProfileProfileDto
import com.nmichail.pizza_shift_2025.data.remote.ProfileApi
import com.nmichail.pizza_shift_2025.data.remote.AuthApi
import com.nmichail.pizza_shift_2025.domain.repository.ProfileRepository
import javax.inject.Inject
import android.util.Log

class ProfileRepositoryImpl @Inject constructor(
    private val profileApi: ProfileApi,
    private val authApi: AuthApi
) : ProfileRepository {
    override suspend fun getProfile(token: String): UserDto? {
        return try {
            val response = authApi.getSession(token)
            if (response.success) response.user else null
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun updateProfile(token: String, user: UserDto): Boolean {
        return try {
            val request = UpdateProfileRequestDto(
                profile = UpdateProfileProfileDto(
                    firstname = user.firstname ?: "",
                    middlename = user.middlename ?: "",
                    lastname = user.lastname ?: "",
                    email = user.email ?: "",
                    city = user.city ?: ""
                ),
                phone = user.phone ?: ""
            )
            profileApi.updateProfile(token, request)
            true
        } catch (e: Exception) {
            false
        }
    }
} 
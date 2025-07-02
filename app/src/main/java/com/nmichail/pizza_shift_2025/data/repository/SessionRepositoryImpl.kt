package com.nmichail.pizza_shift_2025.data.repository

import com.nmichail.pizza_shift_2025.data.local.UserSessionDao
import com.nmichail.pizza_shift_2025.data.local.UserSession
import com.nmichail.pizza_shift_2025.domain.repository.SessionRepository
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val dao: UserSessionDao
) : SessionRepository {

    override suspend fun isAuthorized(): Boolean = dao.getSession()?.isAuthorized == true

    override suspend fun setAuthorized(value: Boolean) {
        dao.saveSession(UserSession(isAuthorized = value))
    }

}
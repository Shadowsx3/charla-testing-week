package com.bassmd.myenchantedgarden

import android.util.Log
import com.bassmd.myenchantedgarden.data.remote.AuthService
import com.bassmd.myenchantedgarden.data.remote.dto.LoginRequest

class UserRepositoryImpl(private val authService: AuthService): UserRepository {
    override suspend fun loginUser(loginRequest: LoginRequest) {
        val a = authService.loginUser(loginRequest).exceptionOrNull()
        Log.w("myApp", a.toString());
    }

}

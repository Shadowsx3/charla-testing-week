package com.bassmd.myenchantedgarden

import android.util.Log
import com.bassmd.myenchantedgarden.data.remote.AuthService
import com.bassmd.myenchantedgarden.data.remote.dto.LoginRequest
import com.bassmd.myenchantedgarden.data.remote.dto.UserModel
import com.bassmd.myenchantedgarden.data.remote.dto.UserResponse
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class UserRepositoryImpl(private val authService: AuthService) : UserRepository {
    override suspend fun loginUser(loginRequest: LoginRequest): UserResponse {
        val a: UserResponse = authService.loginUser(loginRequest).getOrElse {
            UserResponse(user = UserModel("","",true,"",0,0,0,0, Clock.System.now()))
        }

        val isPremium = a.user.toString()
        Log.d("myApp", isPremium)

        return a
    }

}

package com.bassmd.myenchantedgarden.model.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bassmd.myenchantedgarden.repo.UserRepository
import com.bassmd.myenchantedgarden.dto.RegisterRequest
import com.bassmd.myenchantedgarden.dto.StatusModel


class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    var isRegistered by mutableStateOf(false)
    var isBusy by mutableStateOf(false)

    suspend fun register(email: String, name: String, password: String) {
        isBusy = true
        val registerResult: Result<StatusModel> =
            userRepository.register(RegisterRequest(email, name, password))
        registerResult.onSuccess {
            isRegistered = true
        }
        isBusy = false
    }
}
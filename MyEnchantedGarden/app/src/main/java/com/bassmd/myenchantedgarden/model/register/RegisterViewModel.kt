package com.bassmd.myenchantedgarden.model.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bassmd.myenchantedgarden.repo.UserRepository
import com.bassmd.myenchantedgarden.dto.RegisterRequest
import com.bassmd.myenchantedgarden.dto.StatusModel
import com.bassmd.myenchantedgarden.model.app.AppViewModel


class RegisterViewModel(private val userRepository: UserRepository) : AppViewModel(userRepository) {
    var email = mutableStateOf("")
    var name = mutableStateOf("")
    var password = mutableStateOf("")
    var isBusy by mutableStateOf(false)

    suspend fun register(): Boolean {
        isBusy = true
        val result: Result<StatusModel> =
            userRepository.register(RegisterRequest(email.value, name.value, password.value))
        isBusy = false
        result.onFailure {
            showError(it.message.toString())
        }
        return result.isSuccess
    }
}
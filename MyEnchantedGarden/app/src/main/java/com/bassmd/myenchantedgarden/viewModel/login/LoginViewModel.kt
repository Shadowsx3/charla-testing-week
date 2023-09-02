package com.bassmd.myenchantedgarden.viewModel.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.bassmd.myenchantedgarden.repo.UserRepository
import com.bassmd.myenchantedgarden.dto.LoginRequest
import com.bassmd.myenchantedgarden.dto.StatusModel
import com.bassmd.myenchantedgarden.viewModel.app.AppViewModel


class LoginViewModel(private val userRepository: UserRepository) : AppViewModel(userRepository) {
    var isBusy by mutableStateOf(false)
    var email = mutableStateOf("")
    var password = mutableStateOf("")

    suspend fun signIn(): Boolean {
        isBusy = true
        val loginResult: Result<StatusModel> =
            userRepository.login(LoginRequest(email.value, password.value))
        loginResult.fold({
            userRepository.getPlants()
            userRepository.getEvents()
            userRepository.getStore()
            userRepository.getAchievements()
        }, {
            showError(it.message.toString())
        })
        isBusy = false
        return loginResult.isSuccess
    }
}
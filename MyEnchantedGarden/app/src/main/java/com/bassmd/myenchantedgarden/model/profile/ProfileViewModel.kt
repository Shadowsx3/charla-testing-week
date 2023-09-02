package com.bassmd.myenchantedgarden.model.profile

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bassmd.myenchantedgarden.dto.AchievementsRequest
import com.bassmd.myenchantedgarden.repo.UserRepository
import com.bassmd.myenchantedgarden.dto.LoginRequest
import com.bassmd.myenchantedgarden.dto.StatusModel
import com.bassmd.myenchantedgarden.model.app.AppViewModel


class ProfileViewModel(private val userRepository: UserRepository) : AppViewModel(userRepository) {
    var isBusy by mutableStateOf(false)

    suspend fun signOut(): Boolean {
        isBusy = true
        val result: Result<StatusModel> = userRepository.logOut()
        result.onFailure {
            showError(it.message.toString())
        }
        isBusy = false
        return result.isSuccess
    }
}
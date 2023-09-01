package com.bassmd.myenchantedgarden.model.profile

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bassmd.myenchantedgarden.dto.AchievementsRequest
import com.bassmd.myenchantedgarden.repo.UserRepository
import com.bassmd.myenchantedgarden.dto.LoginRequest
import com.bassmd.myenchantedgarden.dto.StatusModel


class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {
    val currentUser = userRepository.currentUser
    val userAchievements = userRepository.userAchievements
    var isLoggedIn by mutableStateOf(false)
    var isBusy by mutableStateOf(false)
    var isUnlocked by mutableStateOf(false)

    suspend fun signOut() {
        isBusy = true
        val loginResult: Result<StatusModel> = userRepository.logOut()
        loginResult.onSuccess {
            isLoggedIn = false
        }
        isBusy = false
    }

    suspend fun unlock(it: String) {
        val unlockResult: Result<StatusModel> = userRepository.unlockAchievements(AchievementsRequest(it))
        unlockResult.onSuccess {
            isUnlocked = true
        }
    }
}
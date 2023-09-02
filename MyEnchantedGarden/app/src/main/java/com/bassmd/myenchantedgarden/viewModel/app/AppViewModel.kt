package com.bassmd.myenchantedgarden.viewModel.app

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import com.bassmd.myenchantedgarden.dto.AchievementsRequest
import com.bassmd.myenchantedgarden.dto.AppError
import com.bassmd.myenchantedgarden.repo.UserRepository
import com.bassmd.myenchantedgarden.dto.StatusModel
import kotlinx.coroutines.flow.MutableStateFlow


open class AppViewModel(private val userRepository: UserRepository) : ViewModel() {
    val currentAppError = MutableStateFlow(AppError(showError = false))
    val currentUser = userRepository.currentUser
    val userAchievements = userRepository.userAchievements

    fun showError(error: String, duration: SnackbarDuration = SnackbarDuration.Short) {
        currentAppError.value = AppError(error, duration, true)
    }

    fun dismissError() {
        currentAppError.value = AppError(showError = false)
    }

    suspend fun unlock(it: String): Boolean {
        val result: Result<StatusModel> =
            userRepository.unlockAchievements(AchievementsRequest(it))
        result.fold({
            showError(it.message)
        }) {
            showError(it.message.toString())
        }
        return result.isSuccess
    }
}
package com.bassmd.myenchantedgarden.model.store

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bassmd.myenchantedgarden.repo.UserRepository
import com.bassmd.myenchantedgarden.dto.LoginRequest
import com.bassmd.myenchantedgarden.dto.PlantRequest
import com.bassmd.myenchantedgarden.dto.StatusModel
import com.bassmd.myenchantedgarden.dto.StoreRequest
import com.bassmd.myenchantedgarden.model.app.AppViewModel
import kotlinx.datetime.Clock


class StoreViewModel(private val userRepository: UserRepository) : AppViewModel(userRepository) {
    val userStore = userRepository.userStore

    suspend fun buy(id: Int): Boolean {
        val result = userRepository.buyItem(StoreRequest(id = id))
        result.fold({
            showError(it.message, SnackbarDuration.Short)
        }) {
            showError(it.message.toString())
        }
        return result.isSuccess
    }
}
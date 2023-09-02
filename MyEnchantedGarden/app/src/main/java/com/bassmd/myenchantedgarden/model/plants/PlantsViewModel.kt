package com.bassmd.myenchantedgarden.model.plants

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bassmd.myenchantedgarden.repo.UserRepository
import com.bassmd.myenchantedgarden.dto.LoginRequest
import com.bassmd.myenchantedgarden.dto.PlantRequest
import com.bassmd.myenchantedgarden.dto.PlantsModel
import com.bassmd.myenchantedgarden.dto.PlayRequest
import com.bassmd.myenchantedgarden.dto.StatusModel
import com.bassmd.myenchantedgarden.model.app.AppViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.Instant


class PlantsViewModel(private val userRepository: UserRepository) : AppViewModel(userRepository) {
    val userPlants = userRepository.userPlants
    suspend fun collect(id: Int): Boolean {
        val result = userRepository.collectPlant(PlantRequest(id = id))
        result.fold({
            showError(it.message)
        }) {
            showError(it.message.toString())
        }
        return result.isSuccess
    }

    suspend fun play(won: Boolean): Boolean {
        val result = userRepository.playGame(PlayRequest(won))
        result.fold({
            showError(it.message)
        }) {
            showError(it.message.toString())
        }
        return result.isSuccess
    }
}
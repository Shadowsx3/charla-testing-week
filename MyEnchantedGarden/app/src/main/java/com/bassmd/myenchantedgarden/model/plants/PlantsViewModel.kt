package com.bassmd.myenchantedgarden.model.plants

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bassmd.myenchantedgarden.repo.UserRepository
import com.bassmd.myenchantedgarden.dto.LoginRequest
import com.bassmd.myenchantedgarden.dto.PlantRequest
import com.bassmd.myenchantedgarden.dto.PlantsModel
import com.bassmd.myenchantedgarden.dto.StatusModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.Instant


class PlantsViewModel(private val userRepository: UserRepository) : ViewModel() {
    val userPlants = userRepository.userPlants
    val currentUser = userRepository.currentUser

    suspend fun updatePlants() {
        userRepository.getPlants()
    }

    suspend fun collect(id: Int): Result<StatusModel> {
        return userRepository.collectPlant(PlantRequest(id = id))
    }
}
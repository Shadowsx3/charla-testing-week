package com.bassmd.myenchantedgarden.model.store

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bassmd.myenchantedgarden.repo.UserRepository
import com.bassmd.myenchantedgarden.dto.LoginRequest
import com.bassmd.myenchantedgarden.dto.PlantRequest
import com.bassmd.myenchantedgarden.dto.StatusModel
import com.bassmd.myenchantedgarden.dto.StoreRequest
import kotlinx.datetime.Clock


class StoreViewModel(private val userRepository: UserRepository) : ViewModel() {
    val userStore = userRepository.userStore
    val currentUser = userRepository.currentUser

    suspend fun buy(id: Int): Result<StatusModel> {
        return userRepository.buyItem(StoreRequest(id = id))
    }
}
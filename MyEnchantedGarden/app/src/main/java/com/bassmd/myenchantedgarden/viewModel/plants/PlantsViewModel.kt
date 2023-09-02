package com.bassmd.myenchantedgarden.viewModel.plants

import com.bassmd.myenchantedgarden.repo.UserRepository
import com.bassmd.myenchantedgarden.dto.PlantRequest
import com.bassmd.myenchantedgarden.dto.PlayRequest
import com.bassmd.myenchantedgarden.viewModel.app.AppViewModel


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
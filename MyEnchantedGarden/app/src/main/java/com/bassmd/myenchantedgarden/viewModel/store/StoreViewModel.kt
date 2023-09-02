package com.bassmd.myenchantedgarden.viewModel.store

import com.bassmd.myenchantedgarden.repo.UserRepository
import com.bassmd.myenchantedgarden.dto.StoreRequest
import com.bassmd.myenchantedgarden.viewModel.app.AppViewModel


class StoreViewModel(private val userRepository: UserRepository) : AppViewModel(userRepository) {
    val userStore = userRepository.userStore

    suspend fun buy(id: Int): Boolean {
        val result = userRepository.buyItem(StoreRequest(id = id))
        result.fold({
            showError(it.message)
        }) {
            showError(it.message.toString())
        }
        return result.isSuccess
    }
}
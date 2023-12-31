package com.bassmd.myenchantedgarden.repo

import com.bassmd.myenchantedgarden.dto.AchievementsModel
import com.bassmd.myenchantedgarden.dto.AchievementsRequest
import com.bassmd.myenchantedgarden.dto.LoginRequest
import com.bassmd.myenchantedgarden.dto.PlantRequest
import com.bassmd.myenchantedgarden.dto.PlantsModel
import com.bassmd.myenchantedgarden.dto.PlayRequest
import com.bassmd.myenchantedgarden.dto.RegisterRequest
import com.bassmd.myenchantedgarden.dto.StatusModel
import com.bassmd.myenchantedgarden.repo.dto.StoreModel
import com.bassmd.myenchantedgarden.dto.StoreRequest
import com.bassmd.myenchantedgarden.repo.dto.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val currentUser: Flow<UserModel>
    val userPlants: Flow<List<PlantsModel>>
    val userStore: Flow<List<StoreModel>>
    val userAchievements: Flow<List<AchievementsModel>>

    suspend fun login(loginRequest: LoginRequest): Result<StatusModel>
    suspend fun logOut(): Result<StatusModel>
    suspend fun register(registerRequest: RegisterRequest): Result<StatusModel>
    suspend fun getPlants(): Result<StatusModel>
    suspend fun collectPlant(plantRequest: PlantRequest): Result<StatusModel>

    suspend fun getAchievements(): Result<StatusModel>

    suspend fun unlockAchievements(achievementsRequest: AchievementsRequest): Result<StatusModel>

    suspend fun getStore(): Result<StatusModel>

    suspend fun getEvents(): Result<StatusModel>

    suspend fun buyItem(storeRequest: StoreRequest): Result<StatusModel>

    suspend fun getUser(): Result<StatusModel>

    suspend fun playGame(playRequest: PlayRequest): Result<StatusModel>
}

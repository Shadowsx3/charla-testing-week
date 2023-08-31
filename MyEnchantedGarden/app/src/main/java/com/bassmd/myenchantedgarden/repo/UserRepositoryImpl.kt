package com.bassmd.myenchantedgarden.repo

import android.util.Log
import com.bassmd.myenchantedgarden.data.remote.achievements.AchievementsService
import com.bassmd.myenchantedgarden.data.remote.auth.AuthService
import com.bassmd.myenchantedgarden.data.remote.plants.PlantsService
import com.bassmd.myenchantedgarden.data.remote.store.StoreService
import com.bassmd.myenchantedgarden.data.remote.user.UserService
import com.bassmd.myenchantedgarden.dto.AchievementsModel
import com.bassmd.myenchantedgarden.dto.AchievementsRequest
import com.bassmd.myenchantedgarden.dto.EventModel
import com.bassmd.myenchantedgarden.dto.LoginRequest
import com.bassmd.myenchantedgarden.dto.PlantRequest
import com.bassmd.myenchantedgarden.dto.PlantsModel
import com.bassmd.myenchantedgarden.dto.PlayRequest
import com.bassmd.myenchantedgarden.dto.RegisterRequest
import com.bassmd.myenchantedgarden.dto.StatusModel
import com.bassmd.myenchantedgarden.dto.StoreItem
import com.bassmd.myenchantedgarden.dto.StoreModel
import com.bassmd.myenchantedgarden.dto.StoreRequest
import com.bassmd.myenchantedgarden.dto.UserModel
import com.bassmd.myenchantedgarden.dto.defaultUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock.System.now

class UserRepositoryImpl(
    private val authService: AuthService,
    private val plantsService: PlantsService,
    private val userService: UserService,
    private val achievementsService: AchievementsService,
    private val storeService: StoreService
) : UserRepository {

    private val userData = MutableStateFlow(defaultUser)

    override val currentUser: Flow<UserModel> = userData

    private val plants = MutableStateFlow<List<PlantsModel>>(listOf())

    override val userPlants: Flow<List<PlantsModel>> =
        plants.map { p -> p.filter { plant -> plant.isUnlocked } }

    private val storeItems = MutableStateFlow<List<StoreItem>>(listOf())

    override val userStore: Flow<List<StoreModel>> = storeItems.map {
        val activeEvents = getActiveEventIds()
        return@map it.filter { s ->
            activeEvents.contains(s.eventId)
        }.map { si ->
            StoreModel(
                si.id,
                si.cost,
                si.name,
                si.description,
                plants.value.first { p -> p.id == si.plantId }.filePath,
                si.isAvailable
            )
        }
    }

    private val achievements = MutableStateFlow<List<AchievementsModel>>(listOf())

    override val userAchievements: Flow<List<AchievementsModel>> =
        achievements.map { p -> p.filter { a -> a.isUnlocked } }

    private var events: List<EventModel> = listOf()

    private fun getActiveEventIds(): List<Int> {
        val today = now()
        return events.filter { e -> e.startDate <= today && e.endDate >= today }.map { e -> e.id }
    }

    override suspend fun login(loginRequest: LoginRequest): Result<StatusModel> {
        val response = authService.loginUser(loginRequest)
        val newUser = response.getOrNull()
        if (newUser != null) {
            userData.value = newUser.user
            return Result.success(StatusModel("success"))
        }
        return Result.failure(Error("Users error"))
    }

    override suspend fun logOut(): Result<StatusModel> {
        val response = authService.logOut()
        response.onSuccess {
            userData.value = defaultUser
        }
        return response
    }

    override suspend fun register(registerRequest: RegisterRequest): Result<StatusModel> {
        return authService.register(registerRequest)
    }

    override suspend fun getPlants(): Result<StatusModel> {
        val response = plantsService.getPlants()
        val newPlants = response.getOrNull()
        if (newPlants != null) {
            plants.value = newPlants.plants
            return Result.success(StatusModel("success"))
        }
        return Result.failure(Error("Plants error"))
    }

    override suspend fun collectPlant(plantRequest: PlantRequest): Result<StatusModel> {
        val response = plantsService.collectPlant(plantRequest)
        val newUser = response.getOrNull()
        if (newUser != null) {
            userData.value = newUser.user
            return getPlants()
        }
        response.onFailure {
            return Result.failure(Error(it.message))
        }
        return Result.failure(Error("IDK"))
    }

    override suspend fun getAchievements(): Result<StatusModel> {
        val response = achievementsService.getAchievements()
        val newAchievements = response.getOrNull()
        if (newAchievements != null) {
            achievements.value = newAchievements.achievements
            return Result.success(StatusModel("success"))
        }
        return Result.failure(Error("Achievements error"))
    }

    override suspend fun unlockAchievements(achievementsRequest: AchievementsRequest): Result<StatusModel> {
        val response = achievementsService.unlockAchievement(achievementsRequest)
        val newAchievements = response.getOrNull()
        if (newAchievements != null) {
            achievements.value = newAchievements.achievements
            getUser()
            getPlants()
            getStore()
            return Result.success(StatusModel("success"))
        }
        response.onFailure {
            return Result.failure(Error(it.message))
        }
        return Result.failure(Error("IDK"))
    }

    override suspend fun getStore(): Result<StatusModel> {
        val response = storeService.getStore()
        val newStore = response.getOrNull()
        if (newStore != null) {
            storeItems.value = newStore.store
            return Result.success(StatusModel("success"))
        }
        return Result.failure(Error("Store error"))
    }

    override suspend fun getEvents(): Result<StatusModel> {
        val response = storeService.getEvents()
        val newEvents = response.getOrNull()
        if (newEvents != null) {
            events = newEvents.events
            return Result.success(StatusModel("success"))
        }
        return Result.failure(Error("Events error"))
    }

    override suspend fun buyItem(storeRequest: StoreRequest): Result<StatusModel> {
        val response = storeService.buyItem(storeRequest)
        val newStore = response.getOrNull()
        if (newStore != null) {
            storeItems.value = newStore.store
            getUser()
            return getPlants()
        }
        response.onFailure {
            return Result.failure(Error(it.message))
        }
        return Result.failure(Error("IDK"))
    }

    override suspend fun getUser(): Result<StatusModel> {
        val response = userService.getProfile()
        val newUser = response.getOrNull()
        if (newUser != null) {
            userData.value = newUser.user
            return Result.success(StatusModel("success"))
        }
        return Result.failure(Error("User error"))
    }

    override suspend fun playGame(playRequest: PlayRequest): Result<StatusModel> {
        val response = userService.playGame(playRequest)
        val newUser = response.getOrNull()
        if (newUser != null) {
            userData.value = newUser.user
            return Result.success(StatusModel("success"))
        }
        response.onFailure {
            return Result.failure(Error(it.message))
        }
        return Result.failure(Error("IDK"))
    }

}

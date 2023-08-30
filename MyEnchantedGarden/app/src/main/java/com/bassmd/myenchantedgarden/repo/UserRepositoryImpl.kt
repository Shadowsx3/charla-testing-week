package com.bassmd.myenchantedgarden.repo

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
import com.bassmd.myenchantedgarden.dto.StoreModel
import com.bassmd.myenchantedgarden.dto.StoreRequest
import com.bassmd.myenchantedgarden.dto.UserModel
import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class UserRepositoryImpl(
    private val authService: AuthService,
    private val plantsService: PlantsService,
    private val userService: UserService,
    private val achievementsService: AchievementsService,
    private val storeService: StoreService
) : UserRepository {

    private var currentUser: UserModel? = null

    private var plants: List<PlantsModel> = listOf()

    private var storeItems: List<StoreModel> = listOf()

    private var achievements: List<AchievementsModel> = listOf()

    private var events: List<EventModel> = listOf()

    override fun getCurrentUser(): UserModel? {
        return currentUser
    }

    override fun getUserPlants(): List<PlantsModel> {
        return plants.filter { p -> p.isUnlocked }
    }

    override fun getUserAchievements(): List<AchievementsModel> {
        return achievements.filter { a -> a.isUnlocked }
    }

    private fun getActiveEventIds(): List<Int> {
        val today = now().toLocalDateTime(TimeZone.currentSystemDefault())
        return events.filter { e -> e.startDate <= today && e.endDate >= today }.map { e -> e.id }
    }

    override fun getStoreItems(): List<StoreModel> {
        val activeEvents = getActiveEventIds()
        return storeItems.filter { s -> s.isAvailable && activeEvents.contains(s.eventId) }
    }

    override suspend fun login(loginRequest: LoginRequest): Result<StatusModel> {
        val response = authService.loginUser(loginRequest)
        val newUser = response.getOrNull()
        if (newUser != null) {
            currentUser = newUser.user
            return Result.success(StatusModel("success", null))
        }
        return Result.failure(Error("Users error"))
    }

    override suspend fun logOut(): Result<StatusModel> {
        val response = authService.logOut()
        response.onSuccess {
            currentUser = null
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
            plants = newPlants.plants
            return Result.success(StatusModel("success", null))
        }
        return Result.failure(Error("Plants error"))
    }

    override suspend fun collectPlant(plantRequest: PlantRequest): Result<StatusModel> {
        val response = plantsService.collectPlant(plantRequest)
        val newUser = response.getOrNull()
        if (newUser != null) {
            currentUser = newUser.user
            return Result.success(StatusModel("success", null))
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
            achievements = newAchievements.achievements
            return Result.success(StatusModel("success", null))
        }
        return Result.failure(Error("Achievements error"))
    }

    override suspend fun unlockAchievements(achievementsRequest: AchievementsRequest): Result<StatusModel> {
        val response = achievementsService.unlockAchievement(achievementsRequest)
        val newAchievements = response.getOrNull()
        if (newAchievements != null) {
            achievements = newAchievements.achievements
            return Result.success(StatusModel("success", null))
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
            storeItems = newStore.store
            return Result.success(StatusModel("success", null))
        }
        return Result.failure(Error("Store error"))
    }

    override suspend fun getEvents(): Result<StatusModel> {
        val response = storeService.getEvents()
        val newEvents = response.getOrNull()
        if (newEvents != null) {
            events = newEvents.events
            return Result.success(StatusModel("success", null))
        }
        return Result.failure(Error("Events error"))
    }

    override suspend fun buyItem(storeRequest: StoreRequest): Result<StatusModel> {
        val response = storeService.buyItem(storeRequest)
        val newStore = response.getOrNull()
        if (newStore != null) {
            storeItems = newStore.store
            return Result.success(StatusModel("success", null))
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
            currentUser = newUser.user
            return Result.success(StatusModel("success", null))
        }
        return Result.failure(Error("User error"))
    }

    override suspend fun playGame(playRequest: PlayRequest): Result<StatusModel> {
        val response = userService.playGame(playRequest)
        val newUser = response.getOrNull()
        if (newUser != null) {
            currentUser = newUser.user
            return Result.success(StatusModel("success", null))
        }
        response.onFailure {
            return Result.failure(Error(it.message))
        }
        return Result.failure(Error("IDK"))
    }

}

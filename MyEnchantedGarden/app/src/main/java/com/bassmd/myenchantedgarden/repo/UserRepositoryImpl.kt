package com.bassmd.myenchantedgarden.repo

import com.bassmd.myenchantedgarden.data.remote.achievements.AchievementsService
import com.bassmd.myenchantedgarden.data.remote.auth.AuthService
import com.bassmd.myenchantedgarden.data.remote.plants.PlantsService
import com.bassmd.myenchantedgarden.data.remote.store.StoreService
import com.bassmd.myenchantedgarden.data.remote.user.UserService
import com.bassmd.myenchantedgarden.dto.*
import com.bassmd.myenchantedgarden.repo.dto.StoreModel
import com.bassmd.myenchantedgarden.repo.dto.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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

    private val plants = MutableStateFlow<List<PlantsModel>>(emptyList())
    override val userPlants: Flow<List<PlantsModel>> =
        plants.map { it.filter { plant -> plant.isUnlocked } }

    private val storeItems = MutableStateFlow<List<StoreItem>>(emptyList())
    override val userStore: Flow<List<StoreModel>> = storeItems.map { storeItems ->
        val activeEvents = getActiveEventIds()
        storeItems
            .filter { si -> activeEvents.contains(si.eventId) }
            .map { si ->
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

    private val achievements = MutableStateFlow<List<AchievementsModel>>(emptyList())
    override val userAchievements: Flow<List<AchievementsModel>> =
        achievements.map { it.filter { a -> a.isUnlocked } }

    private var events: List<EventModel> = emptyList()

    private fun getActiveEventIds(): List<Int> {
        val today = now()
        return events.filter { e -> e.startDate <= today && e.endDate >= today }.map { e -> e.id }
    }

    private suspend fun <T> handleResponse(
        response: Result<T>,
        message: String = "success",
        onSuccess: suspend (T) -> Unit
    ): Result<StatusModel> {
        return response.fold(
            onSuccess = {
                onSuccess(it)
                Result.success(StatusModel(status = "success", message))
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }

    override suspend fun login(loginRequest: LoginRequest): Result<StatusModel> {
        val response = authService.loginUser(loginRequest)
        return handleResponse(response) {
            userData.value = it.user
        }
    }

    override suspend fun logOut(): Result<StatusModel> {
        val response = authService.logOut()
        return handleResponse(response) {
            userData.value = defaultUser
        }
    }

    override suspend fun register(registerRequest: RegisterRequest): Result<StatusModel> =
        handleResponse(authService.register(registerRequest), "🐞 Registration Success 🐞") {}


    override suspend fun getPlants(): Result<StatusModel> =
        handleResponse(plantsService.getPlants()) {
            plants.value = it.plants
        }

    override suspend fun collectPlant(plantRequest: PlantRequest): Result<StatusModel> {
        val response = plantsService.collectPlant(plantRequest)
        return handleResponse(response, "🌿 Plant collected 🌿") {
            userData.value = it.user
            getPlants()
        }
    }

    override suspend fun getAchievements(): Result<StatusModel> =
        handleResponse(achievementsService.getAchievements()) {
            achievements.value = it.achievements
        }

    override suspend fun unlockAchievements(achievementsRequest: AchievementsRequest): Result<StatusModel> {
        val response = achievementsService.unlockAchievement(achievementsRequest)
        return handleResponse(response, "Unlocked successfully ⭐") {
            achievements.value = it.achievements
            getUser()
            getPlants()
            getStore()
        }
    }

    override suspend fun getStore(): Result<StatusModel> =
        handleResponse(storeService.getStore()) {
            storeItems.value = it.store
        }

    override suspend fun getEvents(): Result<StatusModel> =
        handleResponse(storeService.getEvents()) {
            events = it.events
        }

    override suspend fun buyItem(storeRequest: StoreRequest): Result<StatusModel> {
        val response = storeService.buyItem(storeRequest)
        return handleResponse(response, "Enjoy your plant 💖") {
            storeItems.value = it.store
            getUser()
            getPlants()
        }
    }

    override suspend fun getUser(): Result<StatusModel> =
        handleResponse(userService.getProfile()) {
            userData.value = it.user
        }

    override suspend fun playGame(playRequest: PlayRequest): Result<StatusModel> {
        val response = userService.playGame(playRequest)
        return handleResponse(
            response,
            if (playRequest.won) "That's what I call a game 🌻" else "Woops 🥲"
        ) {
            userData.value = it.user
        }
    }
}

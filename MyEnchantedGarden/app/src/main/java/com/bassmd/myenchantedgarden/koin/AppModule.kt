package com.bassmd.myenchantedgarden.koin

import com.bassmd.myenchantedgarden.viewModel.plants.PlantsViewModel
import com.bassmd.myenchantedgarden.viewModel.profile.ProfileViewModel
import com.bassmd.myenchantedgarden.viewModel.register.RegisterViewModel
import com.bassmd.myenchantedgarden.viewModel.store.StoreViewModel
import com.bassmd.myenchantedgarden.repo.UserRepository
import com.bassmd.myenchantedgarden.repo.UserRepositoryImpl
import com.bassmd.myenchantedgarden.data.remote.achievements.AchievementsService
import com.bassmd.myenchantedgarden.data.remote.achievements.AchievementsServiceImpl
import com.bassmd.myenchantedgarden.data.remote.auth.AuthService
import com.bassmd.myenchantedgarden.data.remote.auth.AuthServiceImpl
import com.bassmd.myenchantedgarden.data.remote.plants.PlantsService
import com.bassmd.myenchantedgarden.data.remote.plants.PlantsServiceImpl
import com.bassmd.myenchantedgarden.data.remote.store.StoreService
import com.bassmd.myenchantedgarden.data.remote.store.StoreServiceImpl
import com.bassmd.myenchantedgarden.data.remote.user.UserService
import com.bassmd.myenchantedgarden.data.remote.user.UserServiceImpl
import com.bassmd.myenchantedgarden.viewModel.login.LoginViewModel
import com.bassmd.myenchantedgarden.network.provideKtorClient
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { Json { ignoreUnknownKeys = true } }
    single { provideKtorClient() }
    single<AuthService> { AuthServiceImpl(get(), get()) }
    single<PlantsService> { PlantsServiceImpl(get(), get()) }
    single<UserService> { UserServiceImpl(get(), get()) }
    single<AchievementsService> { AchievementsServiceImpl(get(), get()) }
    single<StoreService> { StoreServiceImpl(get(), get()) }
    single<UserRepository> { UserRepositoryImpl(get(), get(), get(), get(), get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { PlantsViewModel(get()) }
    viewModel { StoreViewModel(get()) }
}
package com.bassmd.myenchantedgarden.koin

import com.bassmd.myenchantedgarden.model.login.LoginViewModel
import com.bassmd.myenchantedgarden.model.plants.PlantsViewModel
import com.bassmd.myenchantedgarden.model.profile.ProfileViewModel
import com.bassmd.myenchantedgarden.model.register.RegisterViewModel
import com.bassmd.myenchantedgarden.model.store.StoreViewModel
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
import com.bassmd.myenchantedgarden.network.provideKtorClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { provideKtorClient() }
    single<AuthService> { AuthServiceImpl(get()) }
    single<PlantsService> { PlantsServiceImpl(get()) }
    single<UserService> { UserServiceImpl(get()) }
    single<AchievementsService> { AchievementsServiceImpl(get()) }
    single<StoreService> { StoreServiceImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get(), get(), get(), get(), get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { PlantsViewModel(get()) }
    viewModel { StoreViewModel(get()) }
}
package com.bassmd.myenchantedgarden

import LoginViewModel
import com.bassmd.myenchantedgarden.data.remote.AuthService
import com.bassmd.myenchantedgarden.data.remote.AuthServiceImpl
import com.bassmd.myenchantedgarden.network.provideKtorClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { provideKtorClient() }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<AuthService> { AuthServiceImpl(get()) }
    viewModel { LoginViewModel(get()) }
}
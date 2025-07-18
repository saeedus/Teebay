package com.sazim.teebay.di

import com.sazim.teebay.auth.presentation.AuthViewModel
import com.sazim.teebay.core.data.remote.ApiConfig
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import com.sazim.teebay.BuildConfig
import com.sazim.teebay.auth.data.repository.AuthRepositoryImpl
import com.sazim.teebay.auth.domain.repository.AuthRepository

val appModule = module {
    //ViewModels
    viewModel { AuthViewModel() }

    single { ApiConfig(BuildConfig.BASE_URL) }

    //repositories
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
}
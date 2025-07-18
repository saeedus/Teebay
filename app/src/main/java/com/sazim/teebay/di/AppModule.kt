package com.sazim.teebay.di

import com.sazim.teebay.auth.presentation.AuthViewModel
import com.sazim.teebay.core.data.remote.ApiConfig
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import com.sazim.teebay.BuildConfig

val appModule = module {
    //ViewModels
    viewModel { AuthViewModel() }

    single { ApiConfig(BuildConfig.BASE_URL) }
}
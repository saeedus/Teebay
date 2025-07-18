package com.sazim.teebay.di

import com.sazim.teebay.auth.data.FcmTokenProvider
import com.sazim.teebay.auth.data.FcmTokenProviderImpl
import com.sazim.teebay.auth.domain.usecase.LoginUseCase
import com.sazim.teebay.auth.presentation.AuthViewModel
import com.sazim.teebay.core.data.remote.ApiConfig
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import com.sazim.teebay.BuildConfig
import com.sazim.teebay.auth.data.repository.AuthRepositoryImpl
import com.sazim.teebay.auth.domain.repository.AuthRepository
import com.sazim.teebay.core.data.remote.HttpClientFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

val appModule = module {
    //ViewModels
    viewModel { AuthViewModel(get()) }

    single { ApiConfig(BuildConfig.BASE_URL) }
    single<HttpClient> {
        HttpClientFactory.create(engine = OkHttp.create())
    }

    //repositories
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }

    //token provider
    single<FcmTokenProvider> { FcmTokenProviderImpl() }

    //use cases
    factory { LoginUseCase(get()) }
}
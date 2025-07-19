package com.sazim.teebay.di

import com.sazim.teebay.auth.data.FcmTokenProvider
import com.sazim.teebay.auth.data.FcmTokenProviderImpl
import com.sazim.teebay.auth.domain.usecase.LoginUseCase
import com.sazim.teebay.auth.domain.usecase.SignUpUseCase
import com.sazim.teebay.auth.presentation.AuthViewModel
import com.sazim.teebay.auth.data.local.SessionManagerImpl
import com.sazim.teebay.core.data.remote.ApiConfig
import com.sazim.teebay.auth.domain.local.SessionManager
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import com.sazim.teebay.BuildConfig
import com.sazim.teebay.auth.data.repository.AuthRepositoryImpl
import com.sazim.teebay.auth.domain.repository.AuthRepository
import com.sazim.teebay.core.data.remote.HttpClientFactory
import com.sazim.teebay.products.presentation.ProductsViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

val appModule = module {
    //ViewModels
    viewModel { AuthViewModel(get(), get(), get()) }
    viewModel { ProductsViewModel(get()) }

    single { ApiConfig(BuildConfig.BASE_URL) }
    single<HttpClient> {
        HttpClientFactory.create(engine = OkHttp.create())
    }

    //repositories
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }

    //token provider
    single<FcmTokenProvider> { FcmTokenProviderImpl() }

    //session manager
    single<SessionManager> { SessionManagerImpl(get()) }

    //use cases
    factory { LoginUseCase(get()) }
    factory { SignUpUseCase(get()) }
}
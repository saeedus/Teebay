package com.sazim.teebay.di

import com.sazim.teebay.auth.data.FcmTokenProvider
import com.sazim.teebay.auth.data.FcmTokenProviderImpl
import com.sazim.teebay.auth.domain.usecase.LoginUseCase
import com.sazim.teebay.auth.domain.usecase.SignUpUseCase
import com.sazim.teebay.auth.presentation.SignInViewModel
import com.sazim.teebay.auth.presentation.SignUpViewModel
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
import com.sazim.teebay.core.presentation.BiometricAuthManager
import com.sazim.teebay.products.data.repository.ProductRepositoryImpl
import com.sazim.teebay.products.domain.repository.ProductRepository
import com.sazim.teebay.products.domain.usecase.AddProductUseCase
import com.sazim.teebay.products.domain.usecase.BuyProductUseCase
import com.sazim.teebay.products.domain.usecase.DeleteProductUseCase
import com.sazim.teebay.products.domain.usecase.GetAllProductsUseCase
import com.sazim.teebay.products.domain.usecase.GetCategoriesUseCase
import com.sazim.teebay.products.domain.usecase.GetMyProductsUseCase
import com.sazim.teebay.products.domain.usecase.GetProductUseCase
import com.sazim.teebay.products.domain.usecase.ProductRentUseCase
import com.sazim.teebay.products.domain.usecase.UpdateProductUseCase
import com.sazim.teebay.products.domain.usecase.GetBoughtProductsUseCase
import com.sazim.teebay.products.domain.usecase.GetBorrowedProductsUseCase
import com.sazim.teebay.products.domain.usecase.GetLentProductsUseCase
import com.sazim.teebay.products.domain.usecase.GetSoldProductsUseCase

val appModule = module {
    //ViewModels
    viewModel { SignInViewModel(get(), get()) }
    viewModel { SignUpViewModel(get(), get()) }
    viewModel {
        ProductsViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

    single { ApiConfig(BuildConfig.BASE_URL) }
    single<HttpClient> {
        HttpClientFactory.create(engine = OkHttp.create())
    }

    //repositories
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
    single<ProductRepository> { ProductRepositoryImpl(get(), get()) }

    //token provider
    single<FcmTokenProvider> { FcmTokenProviderImpl() }

    //session manager
    single<SessionManager> { SessionManagerImpl(get()) }

    //use cases
    factory { LoginUseCase(get()) }
    factory { SignUpUseCase(get()) }
    factory { GetAllProductsUseCase(get()) }
    factory { AddProductUseCase(get()) }
    factory { GetMyProductsUseCase(get()) }
    factory { GetCategoriesUseCase(get()) }
    factory { DeleteProductUseCase(get()) }
    factory { GetProductUseCase(get()) }
    factory { UpdateProductUseCase(get()) }
    factory { BuyProductUseCase(get()) }
    factory { ProductRentUseCase(get()) }
    factory { GetBoughtProductsUseCase(get()) }
    factory { GetSoldProductsUseCase(get()) }
    factory { GetBorrowedProductsUseCase(get()) }
    factory { GetLentProductsUseCase(get()) }

    //biometric manager
    factory { BiometricAuthManager(get()) }
}
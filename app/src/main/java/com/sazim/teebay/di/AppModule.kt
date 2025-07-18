package com.sazim.teebay.di

import com.sazim.teebay.auth.presentation.AuthViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    //ViewModels
    viewModel { AuthViewModel() }
}
package com.example.take_my_money.presentation.di

import com.example.take_my_money.domain.usecases.AllCoinUseCase
import com.example.take_my_money.presentation.viewmodel.CoinDetailsViewModel
import com.example.take_my_money.presentation.viewmodel.CoinListViewModel
import com.example.take_my_money.presentation.viewmodel.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun loadPresentationModule(): Module {
    return presentationModule
}

private val presentationModule = module {

    viewModel {
        CoinListViewModel(
            AllCoinUseCase(get()),
            get()
        )
    }
    viewModel {
        CoinDetailsViewModel(
            get()
        )
    }
    viewModel {
        FavoriteViewModel(
            get()
        )
    }
}

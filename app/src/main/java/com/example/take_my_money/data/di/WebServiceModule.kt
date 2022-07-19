package com.example.take_my_money.data.di

import com.example.take_my_money.data.api.IWebService
import com.example.take_my_money.data.dao.CoinDataBase
import com.example.take_my_money.data.repository.RepositoryDataSource
import com.example.take_my_money.domain.abstracts.UseCaseDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

fun loadWebService(): Module {
    return webServiceModule
}

val webServiceModule = module {
    single {
        IWebService.getBaseUrl()
    }

    single {
        CoinDataBase.getInstance(androidContext()).iCoinDAO
    }

    single<UseCaseDataSource> {
        RepositoryDataSource(get())
    }
}

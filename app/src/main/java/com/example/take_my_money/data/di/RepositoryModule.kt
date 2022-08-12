package com.example.take_my_money.data.di

import com.example.take_my_money.data.repository.RepositoryAllCoins
import com.example.take_my_money.domain.abstracts.ICoinAllAbstract
import org.koin.core.module.Module
import org.koin.dsl.module

fun loadRepositoryModule(): Module {
    return repositoryModule()
}

private fun repositoryModule(): Module {
    return module {
        single<ICoinAllAbstract> {
            RepositoryAllCoins(get())
        }
    }
}

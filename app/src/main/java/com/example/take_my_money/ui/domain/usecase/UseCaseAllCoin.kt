package com.example.take_my_money.ui.domain.usecase

import com.example.take_my_money.ui.data.dao.CoinEntity
import com.example.take_my_money.ui.data.repository.RepositoryAllCoins
import retrofit2.await

class UseCaseAllCoin(private val repositoryAllCoins: RepositoryAllCoins) {

    suspend fun getListCoin(): List<CoinEntity> {
        return getCoinRepository()
    }

    private suspend fun getCoinRepository(): List<CoinEntity> {
        return repositoryAllCoins.getAllCoins().await().filter { it.type_is_crypto == 1 }
    }
}

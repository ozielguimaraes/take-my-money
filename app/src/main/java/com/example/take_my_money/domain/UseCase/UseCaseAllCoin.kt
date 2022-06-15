package com.example.take_my_money.domain.UseCase

import com.example.take_my_money.data.dao.CoinEntity
import com.example.take_my_money.data.repository.RepositoryAllCoins
import retrofit2.await

class UseCaseAllCoin(private val repositoryAllCoins: RepositoryAllCoins) {

    suspend fun getListCoin(): List<CoinEntity> {
        return getListCoins()
    }

    private suspend fun getListCoins(): List<CoinEntity> {
        return repositoryAllCoins.getAllCoins().await().filter { it.type_is_crypto == 1 }
    }
}

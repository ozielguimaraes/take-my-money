package com.example.take_my_money.ui.repository

import com.example.take_my_money.ui.data.entity.CoinEntity

interface IRepositoryDataSource {

    suspend fun insertCoinI(
        coin: List<CoinEntity>
    ): List<Long>

    suspend fun update(coin: List<CoinEntity>)

    suspend fun deleteCoin(id: Long)

    suspend fun getAllCoins(): List<CoinEntity>
}

package com.example.take_my_money.ui.repository

import com.example.take_my_money.ui.data.entity.CoinEntity

interface IRepositoryDataSource {

    suspend fun insertCoinI(
        coin: CoinEntity
    ): Long

    suspend fun deleteCoin(id: Long)
}

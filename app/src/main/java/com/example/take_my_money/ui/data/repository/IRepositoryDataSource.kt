package com.example.take_my_money.ui.data.repository

import com.example.take_my_money.ui.data.dao.CoinEntity

interface IRepositoryDataSource {

    suspend fun insertCoinI(coin: CoinEntity): Long

    suspend fun deleteCoin(name: String)

    suspend fun getAllCoins(): List<CoinEntity>

    suspend fun getByAssetId(assetId: String): CoinEntity?
}

package com.example.take_my_money.ui.domain.usecase

import com.example.take_my_money.ui.data.dao.CoinEntity

interface UseCaseDataSource {

    suspend fun insertCoinI(coin: CoinEntity): Long

    suspend fun deleteCoin(name: String)

    suspend fun getAllCoins(): List<CoinEntity>

    suspend fun getByAssetId(assetId: String): CoinEntity?
}

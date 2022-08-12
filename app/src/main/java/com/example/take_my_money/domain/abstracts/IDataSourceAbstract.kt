package com.example.take_my_money.domain.abstracts

import com.example.take_my_money.data.dao.CoinEntity

interface IDataSourceAbstract {

    suspend fun insertCoinI(coin: CoinEntity): Long

    suspend fun deleteCoin(name: String)

    suspend fun getAllCoins(): List<CoinEntity>

    suspend fun getByAssetId(currencyAbbreviation: String): CoinEntity?
}

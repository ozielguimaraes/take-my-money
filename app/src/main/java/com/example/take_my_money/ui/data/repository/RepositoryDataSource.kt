package com.example.take_my_money.ui.data.repository

import com.example.take_my_money.ui.data.dao.CoinEntity
import com.example.take_my_money.ui.data.dao.ICoinDAO

class RepositoryDataSource(private val iCoinDAO: ICoinDAO) : IRepositoryDataSource {

    override suspend fun insertCoinI(coin: CoinEntity): Long {
        return iCoinDAO.insert(coin = coin)
    }

    override suspend fun deleteCoin(name: String) {
        return iCoinDAO.delete(name)
    }

    override suspend fun getAllCoins(): List<CoinEntity> {
        return iCoinDAO.allCoin()
    }

    override suspend fun getByAssetId(assetId: String): CoinEntity? {
        return iCoinDAO.getByAssetId(assetId)
    }
}

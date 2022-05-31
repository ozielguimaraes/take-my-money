package com.example.take_my_money.ui.repository

import com.example.take_my_money.ui.data.dao.ICoinDAO
import com.example.take_my_money.ui.data.entity.CoinEntity

class RepositoryDataSource(private val iCoinDAO: ICoinDAO) : IRepositoryDataSource {

    override suspend fun insertCoinI(coin: CoinEntity): Long {
        return iCoinDAO.insert(coin = coin)
    }

    override suspend fun deleteCoin(name: String) {
        return iCoinDAO.delete(name)
    }

    override suspend fun getAllCoins(): Array<CoinEntity> {
        return iCoinDAO.allCoin()
    }

    override suspend fun getByAssetId(assetId: String): CoinEntity {
        return iCoinDAO.getByAssetId(assetId)
    }
}

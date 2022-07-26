package com.example.take_my_money.data.repository

import com.example.take_my_money.data.dao.CoinEntity
import com.example.take_my_money.data.dao.ICoinDAO
import com.example.take_my_money.domain.abstracts.IDataSourceAbstract

class RepositoryDataSource(private val iCoinDAO: ICoinDAO) : IDataSourceAbstract {

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
